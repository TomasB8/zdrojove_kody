<!DOCTYPE html>
<html lang="sk">
<head>
<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-162012046-1"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-162012046-1');
</script>

    <meta charset="UTF-8">
    <title>Otuzovanie</title>
    <link rel="stylesheet" type="text/css" href="style.css">
    <link rel="shortcut icon" href="obrazky/otuzovanie_icon.ico" type="image/x-icon">
    <meta name="description" content="Na tejto stránke vám ponúkame základy otužovania a tiež možnosť zaznamenávania vašich výsledkov, ako aj prehľad miest na otužovanie na mape.">
    <meta name="keywords" content="otuzovanie, studena voda, chlad, zima, vysledky, zaklady otuzovania, ako zacat s otuzovanim, zaznamenavanie, casy">
    <script src="https://www.gstatic.com/firebasejs/8.2.9/firebase-app.js"></script>
    <script src="https://www.gstatic.com/firebasejs/8.2.9/firebase-auth.js"></script>
    <script src="https://www.gstatic.com/firebasejs/8.2.9/firebase-database.js"></script>
    <script src="https://www.gstatic.com/firebasejs/8.2.9/firebase-firestore.js"></script>
    <script src="https://www.gstatic.com/firebasejs/8.2.9/firebase-analytics.js"></script>
</head>
<body>
<?php $actual_page = "prihlasenie"; ?>
    <div id="main">
        <?php include "ine/header.php"; ?>
        <div class="odsadenie">

        <div class="text">
        <div id="sprava2"></div>
        <form class="formular" action="">
        <h1 class="login-title">Registrácia</h1>
        <input id="inMeno" type="text" name="meno" placeholder="Meno" required />
        <input id="inEmail" type="email" name="email" placeholder="Email">
        <input id="inPassword" type="password" name="heslo" placeholder="Heslo">
        <input id="inSecPassword" type="password" name="heslo_znovu" placeholder="Heslo znovu">
        <input id="btnSignUp" type="button" name="submit" value="Registrovať">
        <p class="link">Už ste členom? <a href="login.php">Prihlásenie</a></p>
        </form>
        </div>
</div>
    </div>
    <?php include "ine/footer.php" ?>
    <script src="javascript/registracia.js"></script>
</body>
</html>