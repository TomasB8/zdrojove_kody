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
    
    const inMeno = document.getElementById("inMeno");
    const inEmail = document.getElementById("inEmail");
    const inPassword = document.getElementById("inPassword");
    const inSecPassword = document.getElementById("inSecPassword");
    const btnSignUp = document.getElementById("btnSignUp");
    const auth = firebase.auth();
    var zad = document.getElementById("zadavanie");
    var tab = document.getElementById("tabulka");

    btnSignUp.addEventListener('click', e => {
        const meno = inMeno.value;
        const email = inEmail.value;
        const pass = inPassword.value;
        const secPass = inSecPassword.value;
    
        if(pass == secPass){
            if(meno === ""){
                const sprava = document.getElementById("sprava2");
                sprava.classList.add("error");
                sprava.innerText = "Vyplňte meno, prosím.";
            }else{
                const promise = auth.createUserWithEmailAndPassword(email, pass);
                promise.catch(e => {
                    const sprava = document.getElementById("sprava2");
                    sprava.classList.add("error");
                    sprava.innerText = "Pri registrácii sa vyskytla chyba.\nSkontrolujte údaje, prosím.";
                });
            }
        }else{
            const sprava = document.getElementById("sprava2");
            sprava.classList.add("error");
            sprava.innerText = "Heslá nie sú rovnaké.";
        }
    });

    firebase.auth().onAuthStateChanged(firebaseUser => {
        const meno = inMeno.value;
        const email = inEmail.value;
        
        if(firebaseUser){
            zad.href = "zadavanie.php";
            tab.href = "tabulka.php";
            writeInfo(meno, email, firebaseUser.uid); 
        }else{
            zad.href = "#";
            tab.href = "#";
        }
    })
}());

function writeInfo (meno, email, uid){
    firebase.firestore().collection("pouzivatelia").doc(uid).collection("informacie").doc("info").set({
        meno: meno,
        email: email,
      }).then(function() {
        const sprava = document.getElementById("sprava2");
        sprava.classList.add("success");
        sprava.innerText = "Registrácia prebehla úspešne.";
        firebase.auth().signOut();
        window.location.replace("login.php");
    }).catch(function(error) {
        const sprava = document.getElementById("sprava2");
        sprava.classList.add("error");
        sprava.innerText = "Pri registrácii sa vyskytla chyba. Skúste to ešte raz.";
    });
}