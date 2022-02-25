(function(){

    var zadavanie = document.getElementById("zadavanie");
    var tabulka = document.getElementById("tabulka");
    var prihlasenie = document.getElementById("prihlasenie");
    var text = document.getElementById("pText"); 

    pText.style.visibility = "hidden";

    firebase.auth().onAuthStateChanged(firebaseUser => {
        if(firebaseUser){
            zadavanie.href="zadavanie.php";
            tabulka.href = "tabulka.php"
            prihlasenie.innerText = "Profil";
            prihlasenie.href="profil.php";
        }else{
            zadavanie.href="login.php";
            tabulka.href="login.php";
            prihlasenie.href="login.php";
            prihlasenie.innerText = "Prihlásenie";
        }
    })
}());