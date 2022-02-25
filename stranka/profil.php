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
        
        <div class="text alignCenter">
            <div class='pozdrav'>
                <h1 id="menoHeading">Vitaj!</h1>
            </div>
            <div class="profil">
                <img src="obrazky/pouzivatel.png" class="user" width="200" height="200">
            </div>
                <input id="btnLogout" class="odhlasenie" type="button" name="odhlasit" value="Odhlásiť sa">

        </div>
</div>
    </div>
    <?php include "ine/footer.php" ?>
    <script src="javascript/profil.js"></script>
    <script src="javascript/prihlaseny.js"></script>
</body>
</html>