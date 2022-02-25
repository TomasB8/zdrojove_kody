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

    const nazov = document.getElementById("miesto");
    const teplota = document.getElementById("poslednaTeplota");
    const pridat = document.getElementById("btnNoveMiesto");
    let obrazok = document.getElementById("obrMiesto");
    
    firebase.auth().onAuthStateChanged(firebaseUser => {
        if(firebaseUser){
            pridat.addEventListener("click", e => {
                let urlParams = new URLSearchParams(window.location.search);
                let lat = parseFloat(urlParams.get("lat"));
                let lng = parseFloat(urlParams.get("lng"));
                let vDatum;
                let miesto = nazov.value;
                let vVoda = (teplota.value).replace(/,/g, '\.');
                let teplota1 = parseFloat(vVoda);
                let teplota2;
                let uuid = uuidv4()

                if(isNaN(teplota1)){
                    teplota2 = -654;
                    vDatum = "";
                }else{
                    teplota2 = teplota1;
                    vDatum = formatDate();
                }

                if(nazov.value === ""){
                    const sprava = document.getElementById("sprava5");
                    sprava.classList.add("error");
                    sprava.innerText = "Zadajte názov miesta";
                }else{
                    if(obrazok.src !== "http://otuzovanie.wz.sk/obrazky/obrazok_placeholder.png"){
                        uploadImage(uuid, files);
                    }
                    writeData(miesto, vDatum, teplota2, lat, lng, firebaseUser.uid, uuid);
                }
            })
        }else{
            window.location.href = "login.php";
        }
    })
}());

var files = [];
var reader;
var obrMiesto = document.getElementById("obrMiesto");

obrMiesto.addEventListener("click", e => {
    var input = document.createElement("input");
    input.type = "file";
        
    input.onchange = e => {
        files = e.target.files;
        reader = new FileReader();
        reader.onload = function(){
            obrMiesto.src = reader.result;
        }
        reader.readAsDataURL(files[0]);
    }
    input.click();
})

function writeData (nazov, datum, teplota, latitude, longtitude, uid, uuidv4){
    firebase.database().ref("miesta/miesto_" + uuidv4).set({
        nazov: nazov,
        datum: datum,
        teplota: teplota,
        latitude: latitude,
        longtitude: longtitude,
        userId: uid,
        miestoId: "miesto_"+uuidv4
    }).then(function() {
        window.location.href = "mapa.php";
    }).catch(function(error) {
        //console.error("Error writing document: ", error);
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
    var uploadTask = firebase.storage().ref("miesto_"+miestoId + "/Obrazky/miesto").put(files[0]);

    uploadTask.on("state_changed", e => {
    },
    function(error){
        const sprava = document.getElementById("sprava5");
        sprava.classList.add("error");
        sprava.innerText = "Pri ukladaní obrázka sa vyskytla chyba.";
    })
}