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
  const txtPassword = document.getElementById("txtPassword");
  const btnLogin = document.getElementById("btnLogin");
  const sprava = document.getElementById("sprava");
  const auth = firebase.auth();
  var zad = document.getElementById("zadavanie");
  var tab = document.getElementById("tabulka");

  btnLogin.addEventListener('click', e => {
    const email = txtEmail.value;
    const pass = txtPassword.value;

    const promise = auth.signInWithEmailAndPassword(email, pass);
    promise.catch(e => {
      sprava.classList.add("error");
      sprava.innerText = "Prihlasovanie zlyhalo.\nSkontrolujte meno a heslo.";
    })
  });

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