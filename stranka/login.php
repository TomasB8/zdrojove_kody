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
    <script src="https://www.gstatic.com/firebasejs/8.2.9/firebase-analytics.js"></script>
</head>
<body>
<?php $actual_page = "prihlasenie"; ?>
    <div id="main">
        <?php include "ine/header.php"; ?>
        <div class="odsadenie">
        <div class="text alignCenter">

        <div id="sprava"></div>
        <form class="formular" method="post" name="login">
        <h1>Prihlásenie</h1>
        <input id="txtEmail" type="email" name="email" placeholder="Email" autofocus="true"/>
        <input id="txtPassword" type="password" name="heslo" placeholder="Heslo">
        <input id="btnLogin" type="button" value="Prihlásiť sa" name="submit"/>
    <p class="link"> Zabudli ste heslo? Kliknite <a href="zabudnute_heslo.php">tu</a></p>
  <p class="link"> Ešte nie ste členom? <a href="registracia.php">Registrácia</a></p>
  </form>
        </div>
        </div>
    </div>
    <?php include "ine/footer.php" ?>
    <script src="javascript/login.js"></script>
    <script src="javascript/prihlaseny.js"></script>
</body>
</html>