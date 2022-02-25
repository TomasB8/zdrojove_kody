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
<?php $actual_page = "zadavanie"; ?>
    <div id="main">
        <?php include "ine/header.php"; ?>
        <div class="odsadenie">
        
        <div class="text alignCenter">
        <div id="sprava1"></div>
        <h1>Pridanie nového záznamu</h1>
        <form class="formular zadavanie" action="">
                <label for="datum">Dátum:</label><br>
                <input type="date" name="datum" class="vacsi" id="datum" value="<?php echo date("Y-m-d"); ?>"><br>
                <label for="cas">Čas:</label><br>
                <input type="time" name="cas" id="cas" class="vacsi" value="<?php echo date("H:i");?>"><br>
                <label for="cas_vo_vode">Čas vo vode:</label><br>
                <input type="number" name="minuty" id="minuty" placeholder="00" step="1" min="0" max="59">:
                <input type="number" name="sekundy" id="sekundy"
                placeholder="00" step="1" min="0" max="59"><br>
                <label for="voda">Teplota vody:</label><br>
                <input type="number" name="voda" id="voda" value="<?php echo $_POST['voda'];?>" step="any">°C<br>
                <label for="vzduch">Teplota vzduchu:</label><br>
                <input type="number" name="vzduch" id="vzduch" value="<?php echo $_POST['vzduch'];?>" step="any">°C<br><br>
                <input id="btnNovyZaznam" type="button" name="pridat" value="Pridať nový záznam">
            </form>
        </div>
    </div>
</div>
    <?php include "ine/footer.php" ?>
    <script src="javascript/zadavanie.js"></script>
    <script src="javascript/prihlaseny.js"></script>
</body>
</html>