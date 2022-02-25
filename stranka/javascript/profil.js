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
    
    const btnLogout = document.getElementById("btnLogout");
    const auth = firebase.auth();

    firebase.auth().onAuthStateChanged(firebaseUser => {
        if(firebaseUser){
            firebase.firestore().collection("pouzivatelia").doc(firebaseUser.uid).collection("informacie").doc("info")
            .get().then((snapshot) => {
                document.getElementById("menoHeading").innerText = "Vitaj " + snapshot.data().meno + "!";
            })
        }else{
            window.location.href = "login.php";
        }
    })

    btnLogout.addEventListener("click", e => {
        auth.signOut();
        window.location.replace("login.php");
    })
}());