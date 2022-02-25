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
  
    const txtEmail = document.getElementById("txtEmail");
    const btnReset = document.getElementById("btnPassReset");
    const sprava = document.getElementById("sprava7");
    const auth = firebase.auth();
    var zad = document.getElementById("zadavanie");
    var tab = document.getElementById("tabulka");

    btnReset.addEventListener("click", e => {
        const email = txtEmail.value;

        if(email !== ""){
            firebase.auth().sendPasswordResetEmail(email);
            window.location.href = "login.php";
        }else{
            sprava.classList.add("error");
            sprava.innerText = "Zadajte email na resetovanie hesla";
        }
    })
  
    firebase.auth().onAuthStateChanged(firebaseUser => {
      if(firebaseUser){
          window.location = "zadavanie.php";
          zad.href = "zadavanie.php";
          tab.href = "tabulka.php";
      }else{
          zad.href = "#";
          tab.href = "#";
      }
  })
}());