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
    
    const datum = document.getElementById("datum");
    const cas = document.getElementById("cas");
    const minuty = document.getElementById("minuty");
    const sekundy = document.getElementById("sekundy");
    const voda = document.getElementById("voda");
    const vzduch = document.getElementById("vzduch");
    const ulozit = document.getElementById("btnUlozitZaznam");
    const auth = firebase.auth();

    firebase.auth().onAuthStateChanged(firebaseUser => {
        if(firebaseUser){
            loadData(firebaseUser.uid);
            ulozit.addEventListener("click", e => {
                const vDatum = formatDate(datum.value);
                const vCas = cas.value;
                let splitCas = vCas.split(":");
                let numCas = parseInt(splitCas[0] + splitCas[1]);
                let vMinuty;
                let vSekundy;
                let numMinuty;
                let numSekundy;
                if(minuty.value === ""){
                    numMinuty = 00;
                }else{
                    vMinuty = ("0" + minuty.value).slice(-2);
                    numMinuty = parseInt(vMinuty);
                }
                if(sekundy.value === ""){
                    numSekundy = 00;
                }else{
                    vSekundy = ("0" + sekundy.value).slice(-2);
                    numSekundy = parseInt(vSekundy);
                }
                const vVoda = (voda.value).replace(/,/g, '\.');
                const vVzduch = (vzduch.value).replace(/,/g, '\.');
                let numVoda = parseFloat(vVoda);
                let numVzduch = parseFloat(vVzduch);

                if(datum.value === "" || vCas === ""){
                    const sprava = document.getElementById("spravaUprava");
                    sprava.classList.add("error");
                    sprava.innerText = "Vyplňte aspoň dátum a čas";
                }else{
                    writeData(vDatum, vCas, numCas, numMinuty, numSekundy, numVoda, numVzduch, epochDate(vDatum), firebaseUser.uid);
                }
            })
        }else{
            window.location.href = "login.php";
        }
    })     
}());

function writeData (datum, cas, numCas, minuty, sekundy, voda, vzduch, epochDatum, uid){
    let urlParams = new URLSearchParams(window.location.search);
    let url = urlParams.get("id");
    firebase.firestore().collection("pouzivatelia").doc(uid).collection("zaznamy").doc(url).set({
        datum: datum,
        cas: cas,
        orderCas: numCas,
        minuty: minuty,
        sekundy: sekundy,
        voda: voda,
        vzduch: vzduch,
        epochDatum: epochDatum,
        zaznamId: url
    }).then(function() {
        window.location.href = "tabulka.php";
    }).catch(function(error) {
        //console.error("Error writing document: ", error);
        const sprava = document.getElementById("spravaUprava");
        sprava.classList.add("error");
        sprava.innerText = "Pri zadávaní dát do databázy sa vyskytla chyba.";
    });
}

function formatDate(datum){
    var date = new Date(datum);
    return ("0" + date.getDate()).slice(-2) + '.' + ("0" + (date.getMonth() + 1)).slice(-2) + '.' + date.getFullYear();
}

function formatDate1(datum){
    var date = new Date(datum);
    return date.getFullYear() + '-' + ("0" + (date.getMonth() + 1)).slice(-2) + '-' + ("0" + date.getDate()).slice(-2);
}

function loadData(uid){
    let urlParams = new URLSearchParams(window.location.search);
    let id = urlParams.get("id");
    firebase.firestore().collection("pouzivatelia").doc(uid).collection("zaznamy").doc(id)
        .get().then((snapshot) => {
            var date = (snapshot.data().datum).split("\.");
            var dateObject = new Date(date[2], date[1]-1, date[0]);
            datum.value = formatDate1(dateObject);
            cas.value = snapshot.data().cas;
            minuty.value = ("0" + parseInt(snapshot.data().minuty)).slice(-2);
            sekundy.value = ("0" + parseInt(snapshot.data().sekundy)).slice(-2);
            voda.value = ("" + snapshot.data().voda).replace(/,/g, '\.');
            vzduch.value = ("" + snapshot.data().vzduch).replace(/,/g, '\.');
        })
}

function epochDate(datum){
    var date = datum.split("\.");
    var epoch = new Date(parseInt(date[2]), parseInt(date[1])-1, parseInt(date[0])).getTime();
    return epoch;
}