(function(){
    var firebaseConfig = {
        apiKey: "",
        authDomain: "",
        projectId: "",
        storageBucket: "",
        messagingSenderId: "",
        appId: "",
        measurementId: ""
    };
    // Initialize Firebase
    firebase.initializeApp(firebaseConfig);
    firebase.analytics();

    const teplota = document.getElementById("editTeplota");
    const ulozit = document.getElementById("btnUlozit");
    const odstranit = document.getElementById("btnOdstranit");
    const galeria = document.getElementById("galeria");

    let urlParams = new URLSearchParams(window.location.search);
    let miestoId = urlParams.get("miestoId");

    loadData(miestoId);
    
    firebase.auth().onAuthStateChanged(firebaseUser => {
        if(firebaseUser){
            var files = [];
            var reader;
            galeria.addEventListener("click", e => {
                var obrUpravit = document.getElementById("obrUpravit");
                var input = document.createElement("input");
                input.type = "file";
            
                input.onchange = e => {
                    files = e.target.files;
                    reader = new FileReader();
                    reader.onload = function(){
                        obrUpravit.src = reader.result;
                    }
                    reader.readAsDataURL(files[0]);
                }
                input.click();
            })

            ulozit.addEventListener("click", e => {
                let lat, lng, nazov, userId;
                let ref = firebase.database().ref("miesta/"+miestoId);
                ref.on('value', (snapshot) => {
                    lat = snapshot.val().latitude;
                    lng = snapshot.val().longtitude;
                    nazov = snapshot.val().nazov;
                    userId = snapshot.val().userId;
                })

                const vDatum = formatDate();
                const vVoda = (teplota.value).replace(/,/g, '\.');
                let numVoda = parseFloat(vVoda);

                if(teplota.value === ""){
                    const sprava = document.getElementById("sprava6");
                    sprava.classList.add("error");
                    sprava.innerText = "Zadajte nameranú teplotu";
                }else{
                    if(obrUpravit.src !== "http://localhost/stranka_otuzovanie/obrazky/obrazok_placeholder.png" && (typeof files[0]) !== "undefined"){
                        uploadImage(miestoId, files);
                    }
                    writeData(nazov, vDatum, numVoda, lat, lng, userId, miestoId);
                }
            })

            odstranit.addEventListener("click", e => {
                let userId;
                let ref = firebase.database().ref("miesta/"+miestoId);
                let storRef = firebase.storage().ref(miestoId+"/Obrazky/miesto");
                ref.on('value', (snapshot) => {
                    userId = snapshot.val().userId;
                })
                if(firebaseUser.uid === userId){
                    var answer = confirm("Naozaj chcete vymazať daný záznam?");
                    if(answer){
                        ref.remove();
                        storRef.delete();
                        window.location.href = "mapa.php";
                    }
                }else{
                    const sprava = document.getElementById("sprava6");
                    sprava.classList.add("error");
                    sprava.innerText = "Na odstránenie tohto miesta nemáte právo.\nOdstrániť miesto môže len ten, kto ho vytvoril";
                }
            })
        }else{
            ulozit.addEventListener("click", e => {
                const sprava = document.getElementById("sprava6");
                sprava.classList.add("error");
                sprava.innerText = "Pre upravovanie musíte byť prihlásený.";
            })

            galeria.addEventListener("click", e => {
                const sprava = document.getElementById("sprava6");
                sprava.classList.add("error");
                sprava.innerText = "Pre upravovanie musíte byť prihlásený.";
            })

            odstranit.addEventListener("click", e => {
                const sprava = document.getElementById("sprava6");
                sprava.classList.add("error");
                sprava.innerText = "Na odstránenie tohto miesta nemáte právo.";
            })
        }
    })
}());

function loadData(miestoId){
    let loader = document.getElementById("loader1");
    const h1 = document.getElementById("editNazov");
    const teplota = document.getElementById("editTeplota");
    const img = document.getElementById("obrUpravit");
    const galeria = document.getElementById("galeria");
    const datum = document.getElementById("dateUprava");
    let ref = firebase.database().ref("miesta/"+miestoId);
    ref.on('value', (snapshot) => {
        h1.innerText = snapshot.val().nazov;
        if(snapshot.val().teplota !== -654){
            teplota.value = snapshot.val().teplota;
        }else{
            teplota.value = "";
        }
            
        datum.innerText = snapshot.val().datum;
    })

    let loadPic = firebase.storage().ref(miestoId+"/Obrazky/miesto")
        .getDownloadURL().then(function(url){
            img.src = url;
            galeria.src = "obrazky/galeria.png";
            if(loader != null){
                loader.remove();
            }
            img.style.height = "200px";
            img.style.marginBottom = "20px";
            img.addEventListener("click", e => {
                window.open(url);
            })
        }).catch(e => {
            console.log(e);
            img.src = "obrazky/obrazok_placeholder.png";
            galeria.src = "obrazky/galeria.png";
            if(loader != null){
                loader.remove();
            }
            img.style.height = "200px";
            img.style.marginBottom = "20px";
        });
}

function writeData (nazov, datum, teplota, latitude, longtitude, uid, miestoId){
    firebase.database().ref("miesta/" + miestoId).set({
        nazov: nazov,
        datum: datum,
        teplota: teplota,
        latitude: latitude,
        longtitude: longtitude,
        userId: uid,
        miestoId: miestoId
    }).then(function() {
        window.location.href = "mapa.php";
    })
    .catch(function(error) {
        const sprava = document.getElementById("sprava5");
        sprava.classList.add("error");
        sprava.innerText = "Pri zadávaní dát do databázy sa vyskytla chyba.";
    });
}

function formatDate(){
    const dateObj = new Date();
    const month = String(dateObj.getMonth() + 1).padStart(2, '0');
    const day = String(dateObj.getDate()).padStart(2, '0');
    const year = dateObj.getFullYear();
    return day + "." + month + "." + year;
}

function uuidv4() {
    return ([1e7]+-1e3+-4e3+-8e3+-1e11).replace(/[018]/g, c =>
        (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
    );
}

function uploadImage(miestoId, files){
    var uploadTask = firebase.storage().ref(miestoId + "/Obrazky/miesto").put(files[0]);

    uploadTask.on("state_changed", e => {
    },
    function(error){
        const sprava = document.getElementById("sprava6");
        sprava.classList.add("error");
        sprava.innerText = "Pri ukladaní obrázka sa vyskytla chyba.";
    })
}