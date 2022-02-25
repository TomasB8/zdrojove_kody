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
    const pridat = document.getElementById("btnNovyZaznam");

    firebase.auth().onAuthStateChanged(firebaseUser => {
        if(firebaseUser){
            pridat.addEventListener("click", e => {
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
                    if((parseInt(vMinuty) % 1) == 0){
                        numMinuty = parseInt(vMinuty);
                    }else{
                        const sprava = document.getElementById("sprava1");
                        sprava.classList.add("error");
                        sprava.innerText = "Minúty ani sekundy nesmú obsahovať desatinné čísla.";
                    }
                }
                if(sekundy.value === ""){
                    numSekundy = 00;
                }else{
                    vSekundy = ("0" + sekundy.value).slice(-2);
                    if((parseInt(vSekundy) % 1) == 0){
                        numSekundy = parseInt(vSekundy);
                    }else{
                        const sprava = document.getElementById("sprava1");
                        sprava.classList.add("error");
                        sprava.innerText = "Minúty ani sekundy nesmú obsahovať desatinné čísla.";
                    }
                }
                const vVoda = (voda.value).replace(/,/g, '\.');
                const vVzduch = (vzduch.value).replace(/,/g, '\.');
                let numVoda = parseFloat(vVoda);
                let numVzduch = parseFloat(vVzduch);

                if(datum.value === "" || vCas === ""){
                    const sprava = document.getElementById("sprava1");
                    sprava.classList.add("error");
                    sprava.innerText = "Vyplňte aspoň dátum a čas";
                }else{
                    writeData(vDatum, vCas, numCas, numMinuty, numSekundy, numVoda, numVzduch, epochDate(vDatum), firebaseUser.uid, uuidv4());
                }
            })
        }else{
            window.location.href = "login.php";
        }
    })    
}());

function writeData (datum, cas, numCas, minuty, sekundy, voda, vzduch, epochDate, uid, uuidv4){
    firebase.firestore().collection("pouzivatelia").doc(uid).collection("zaznamy").doc("zaznam_"+uuidv4).set({
        datum: datum,
        cas: cas,
        orderCas: numCas,
        minuty: minuty,
        sekundy: sekundy,
        voda: voda,
        vzduch: vzduch,
        epochDatum: epochDate,
        zaznamId: "zaznam_"+uuidv4
      }).then(function() {
        window.location.href = "tabulka.php";
    })
    .catch(function(error) {
        //console.error("Error writing document: ", error);
        const sprava = document.getElementById("sprava1");
        sprava.classList.add("error");
        sprava.innerText = "Pri zadávaní dát do databázy sa vyskytla chyba.";
    });
}

function uuidv4() {
    return ([1e7]+-1e3+-4e3+-8e3+-1e11).replace(/[018]/g, c =>
      (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
    );
  }

function formatDate(datum){
    var date = new Date(datum);
    return ("0" + date.getDate()).slice(-2) + '.' + ("0" + (date.getMonth() + 1)).slice(-2) + '.' + date.getFullYear();
}

function epochDate(datum){
    var date = datum.split("\.");
    var epoch = new Date(parseInt(date[2]), parseInt(date[1])-1, parseInt(date[0])).getTime();
    return epoch;
}