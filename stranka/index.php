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
<?php $actual_page = "index"; ?>
    <div id="main">
        <?php include "ine/header.php"; ?>

        <div class="odsadenie">
        <div class="text">
        <div class ="uvod" id="uvod">
                <h3>Chcete si zaznamenávať svoje otužilecké výsledky, sledovať svoj progres a porovnávať svoje výsledky?</h3>
                Pre získanie týchto možností zdarma sa zaregistrujte <a href="registracia.php">tu</a>.<br>
                Ak ste už členom prihláste sa <a href="login.php">tu</a>.
            </div>
        <div class="paragraf">
            <h2>Čo vlastne otužovanie je?</h2>
            <div class="line"></div>
            <div class="blok">
            V skratke, otužovanie je <i>dobrovoľné vystavovanie sa chladu</i>, 
            či už vo vode (sprchovanie sa studenou vodou, plávanie v 
            zamrznutom jazere), alebo aj vo vzduchu (prechádzky v krátkom 
            tričku, behanie, bicyklovanie). Poznáme otužovanie rekreačné a 
            športové. <br><br>
            Pod <b>rekreačným otužovaním</b> rozumieme sprchovanie sa studenou 
            vodou, športovanie po celý rok (aj v zime) vo voľnej prírode, 
            napríklad behanie alebo bicyklovanie. <br><br>
            Pod <b>športovým otužovaním</b> rozumieme plávanie v studenej 
            vode, ktorá má maximálne 10°C. 
        </div>
        </div>
            <div class="paragraf">
                <h2>V čom je otužovanie prospešné?</h2>
                <div class="line"></div>
                <div class="blok">
                Otužovanie blahodárne pôsobí na imunitný systém, 
                termoreguláciu organizmu, cievy, mozog, pokožku, 
                kardiovaskulárny aparát, na telesnú zdatnosť, môže sa 
                vyskytnúť aj zmiernenie alergií, potlačenie nervových a 
                psychických porúch, či posilnenie obranyschopnosti 
                organizmu voči chorobám. Je vedecky dokázané, že otužilci 
                sa lepšie vyrovnávajú s únavou a ľahšie odolávajú chorobám. 
                Ak aj človek ochorie, priebeh choroby je podstatne ľahší a 
                trvá kratšie.
            </div>
        </div>
            <div class="paragraf">
                <h2>Prečo s otužovaním začať?</h2>
                <div class="line"></div>
                <div class="blok">
                Otužovanie je predovšetkým o vystúpení z komfortnej zóny, 
                pretože sa musíte prekonať, vstať z gauča a skočiť do 
                ľadovej vody. Toto sa môže zdať veľmi ťažké, teda aspoň zo 
                začiatku. Ale takéto vystúpenie z komfortnej zóny vám vyčarí 
                úsmev na tvári a po zvyšok dňa budete šťastný a spokojný, 
                že ste sa prekonali. A to som ešte nespomenul všetky benefity, 
                ktoré vám otužovanie ponúka. Tie budú zhrnuté v ďalšom bode.
            </div>
        </div>
            <div class="paragraf">
                <h2>Benefity otužovania</h2>
                <div class="line"></div>
                <div class="blok">
                <ul type="circle">
                    <li>upevňovanie zdravia, pevnej vôle </li>
                    <li>zlepšenie telesnej zdatnosti</li>
                    <li>posilnenie imunitného systému</li>
                    <li>schopnosť ľahšie sa vyrovnať so zimou</li>
                    <li>ústup alergií</li>
                    <li>zníženie stresu</li>
                    <li>dobrá nálada a množstvo energie</li>
                    <li>zlepšenie hormonálnej činnosti a pamäte</li>
                    <li>regenerácia po cvičení</li>
                </ul><br>
                Toto sú len niektoré benefity, ktoré vám otužovanie 
                poskytne, v skutočnosti ich môže byť podstatne viac.<br><br>
                Teraz, keď už vieme o otužovaní toho dosť, poďme sa 
                pozrieť, ako s otužovaním začať.
            </div>
            </div>
            <div class="paragraf">
                <h2>Čo je základom pri otužovaní?</h2>
                <div class="line"></div>
                <div class="blok">
                Základy pri otužovaní sa nedajú jednoznačne určiť, 
                pretože je to individuálna záležitosť. Každý, kto s 
                otužovaním začína si musí zvoliť svoj vlastný postup, 
                ktorý mu vyhovuje najviac. Ide o prekonávanie pocitu 
                chladu a pre mnohých ešte nepríjemnejšieho mokrého chladu 
                na vlastnej koži, čo je pre mnohých, najmä zo začiatku, 
                veľmi ťažko prijateľné. K mokrému chladu sa často pridáva 
                aj otužilecká triaška, s ktorou je potrebné sa vyrovnať.
            </div>
        </div>
        <div class="paragraf">
                <h2>Ako s otužovaním začať?</h2>
                <div class="line"></div>
                <div class="blok">
                S otužovaním by sa najlepšie malo začať do 40 rokov. 
                Základom na začatie otužovania je domáce otužovanie. 
                Začiatočníci by sa mali každý deň sprchovať studenou vodou, 
                pričom by mali začať vlažnou vodou a postupne ju 
                ochladzovať. Najlepšie je otužovanie ráno. Po osušení by 
                vždy mali nasledovať zahrievacie cviky, ktorými sa telo po 
                studenej sprche dostane späť do normálnej teploty.<br><br>
                Po polroku sprchovania sa studenou vodou môžeme pomaly 
                prejsť na otužovanie vo voľnej prírode.<br><br>
                Skutočne otužilým sa človek stáva približne po 2 rokoch 
                otužovania.
            </div>
        </div>
        <div class="paragraf">
                <h2>Ako sa správne otužovať?</h2>
                <div class="line"></div>
                <div class="blok">
                Poradiť niekomu s tým, ako sa treba otužovať je veľmi 
                náročné, pretože každý si musí zvoliť svoj vlastný postup, 
                ktorý mu vyhovuje a s ktorým je spokojný. Aj napriek tomu, 
                vám skúsim popísať, ak to vyhovuje mne. :)<br><br>
                <ul type="circle">
                    <li>pred vstupom do vody je dobré sa rozcvičiť, 
                    rozhýbať, alebo rozdýchať nejakým vhodným dýchacím 
                    cvičením</li>
                    <li>po dôkladnom zahriatí postupne vchádzame do 
                    studenej vody</li>
                    <li>čas, ktorý človek vo vode strávi je veľmi 
                    individuálny, niektorým vyhovuje vzorec <i>„koľko stupňov, 
                    toľko minút“</i>, no v pohode stačí vo vode vydržať <b>2 minúty</b>, 
                    pretože už po 2 minútach sa dostavujú všetky benefity 
                    otužovania</li>
                    <li>keď sa rozhodnete z vody vyjsť, je dobré sa 
                    poutierať, prípadne prezliecť do suchého oblečenia a 
                    začať cvičiť, ale treba dávať pozor na náročnosť 
                    cvikov, aby ste si neublížili, keďže vaše svaly sú po 
                    kúpaní v studenej vode stuhnuté</li>
                    <li>po zahriatí uvidíte tie návaly šťastia a radosti :)</li>
                </ul><br>
                Pri otužovaní treba dbať predovšetkým na zdravie a 
                bezpečnosť. Nič netreba siliť ani preháňať. Keď vám už je 
                poriadna zima, radšej z vody vyjdite a nabudúce to bude 
                snáď lepšie, veď ako sa hovorí <i>„nie každý deň je nedeľa“</i>.
            </div>
            </div>
            <div class="prianie">Prajem vám veľa šťastia v otužovaní a hlavne pevné zdravie!</div>
        </div>
    </div>
</div>
    <?php include "ine/footer.php" ?>
    
    <script>
    var firebaseConfig = {
        apiKey: "AIzaSyBx9X4PDIoBraUT10OwkHFnJ8s_vsXYxVg",
        authDomain: "otuzovanie-ad65c.firebaseapp.com",
        projectId: "otuzovanie-ad65c",
        storageBucket: "otuzovanie-ad65c.appspot.com",
        messagingSenderId: "139476954791",
        appId: "1:139476954791:web:ea01e8cef551c01ea4f582",
        measurementId: "G-L8H19RF698"
    };
    // Initialize Firebase
    firebase.initializeApp(firebaseConfig);
    firebase.analytics();

      firebase.auth().onAuthStateChanged(firebaseUser => {
        if(!firebaseUser){
            document.getElementById("uvod").style.display = "block";
        }
    })
      </script>
    <script src="javascript/prihlaseny.js"></script>
</body>
</html>