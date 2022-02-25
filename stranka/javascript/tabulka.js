const datumSort = document.getElementById("dat");
const casSort = document.getElementById("cas");
const casVoVodeSort = document.getElementById("cvv");
const vodaSort = document.getElementById("vod");
const vzduchSort = document.getElementById("vzd");
let dataCount;

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

    firebase.auth().onAuthStateChanged(firebaseUser => {
        if(firebaseUser){
            let urlParams = new URLSearchParams(window.location.search);
            switch(urlParams.get("sortBy")){
                case "datum":
                    if(urlParams.get("sortType") == "ASC"){
                        firebaseDoc("epochDatum", "asc", "", firebaseUser.uid);
                        datumSort.classList.add("asc");
                    }else{
                        firebaseDoc("epochDatum", "desc", "", firebaseUser.uid);
                        datumSort.classList.add("desc");
                    }
                    break;
                case "cas":
                    if(urlParams.get("sortType") == "ASC"){
                        firebaseDoc("orderCas", "asc", "", firebaseUser.uid);
                        casSort.classList.add("asc");
                    }else{
                        firebaseDoc("orderCas", "desc", "", firebaseUser.uid);
                        casSort.classList.add("desc");
                    }
                    break;
                case "casVoVode":
                    if(urlParams.get("sortType") == "ASC"){
                        firebaseDoc("minuty", "asc", "sekundy", firebaseUser.uid);
                        casVoVodeSort.classList.add("asc");
                    }else{
                        firebaseDoc("minuty", "desc", "sekundy", firebaseUser.uid);
                        casVoVodeSort.classList.add("desc");
                    }
                    break;
                case "voda":
                    if(urlParams.get("sortType") == "ASC"){
                        firebaseDoc("voda", "asc", "", firebaseUser.uid);
                        vodaSort.classList.add("asc");
                    }else{
                        firebaseDoc("voda", "desc", "", firebaseUser.uid);
                        vodaSort.classList.add("desc");
                    }
                    break;
                case "vzduch":
                    if(urlParams.get("sortType") == "ASC"){
                        firebaseDoc("vzduch", "asc", "", firebaseUser.uid);
                        vzduchSort.classList.add("asc");
                    }else{
                        firebaseDoc("vzduch", "desc", "", firebaseUser.uid);
                        vzduchSort.classList.add("desc");
                    }
                    break;
                default:
                    firebaseDoc("epochDatum", "desc", "", firebaseUser.uid);
                    datumSort.classList.add("desc");
            }
        }else{
            window.location.href = "login.php";
        }
    })
}());

function retrieveData(doc){
    const table = document.getElementById("zaznamy");

    let tr = document.createElement("tr");
    let td_datum = document.createElement("td");
    let td_cas = document.createElement("td");
    let td_casVoVode = document.createElement("td");
    let td_voda = document.createElement("td");
    let td_vzduch = document.createElement("td");
    let td_akcie = document.createElement("td");

    let img1 = document.createElement("img");
    img1.src = "obrazky/upravit.png";
    img1.width = "30";
    img1.height = "30";
    img1.addEventListener("click", e => {
        window.location = "uprava.php?id="+doc.data().zaznamId;
    })

    let img2 = document.createElement("img");
    img2.src = "obrazky/zmazat.png";
    img2.width = "30";
    img2.height = "30";
    img2.addEventListener("click", e => {
        var answer = confirm("Naozaj chcete vymazať daný záznam?");
        if(answer){
            firebase.auth().onAuthStateChanged(user => {
                firebase.firestore().collection("pouzivatelia").doc(user.uid).collection("zaznamy").doc(doc.data().zaznamId)
                    .delete().then(function(){
                        window.location = "tabulka.php";
                    });
            })
            //vykonanie, ak pouzivatel potvrdi confirm okno - odkaz sa vykona    
            return true;
        }else{
            //vykonanie, ak pouzivatel zruzi confirm okno - odkaz sa nevykona
            return false;
        }
    })

    tr.setAttribute("data-id", doc.id);
    td_datum.textContent = doc.data().datum;
    td_cas.textContent = doc.data().cas;
    td_casVoVode.textContent = ("0" + doc.data().minuty).slice(-2) + ":" + ("0" + doc.data().sekundy).slice(-2);
    if(doc.data().voda.toString() != "NaN"){
        let sVoda = (doc.data().voda).toString();
        td_voda.textContent = sVoda.replace(/\./g, ',') + " °C";
    }else{
        td_voda.textContent = "";
    }
    if(doc.data().vzduch.toString() != "NaN"){
        let sVzduch = doc.data().vzduch.toString();
        td_vzduch.textContent = sVzduch.replace(/\./g, ',') + " °C";
    }else{
        td_vzduch.textContent = "";
    }
    td_akcie.appendChild(img1);
    td_akcie.appendChild(img2);

    tr.appendChild(td_datum);
    tr.appendChild(td_cas);
    tr.appendChild(td_casVoVode);
    tr.appendChild(td_voda);
    tr.appendChild(td_vzduch);
    tr.appendChild(td_akcie);

    table.appendChild(tr);
}

function firebaseDoc(sortBy, sortType, sortBy2, uid){
    let loader = document.getElementById("loader");
    let pageNum = document.querySelector("h4");
    let lastVisible, firstVisible;
    let page = 1;
    pageNum.innerText = page;
    if(sortBy2 == ""){
        firebase.firestore().collection("pouzivatelia").doc(uid).collection("zaznamy").orderBy(sortBy, sortType).limit(20)
            .get().then((snapshot) => {
                if(snapshot.size > 0){
                snapshot.docs.forEach(doc => {
                        retrieveData(doc);
                        if(loader != null){
                            loader.remove();
                        }
                })
                lastVisible = snapshot.docs[snapshot.docs.length - 1];
            
                }else{
                    const table = document.getElementById("zaznamy");
                    table.style.visibility = "hidden";
                    const sprava = document.getElementById("bezZaznamu");
                    sprava.classList.add("error");
                    sprava.innerText = "Nemáte žiadne záznamy";
                    document.getElementById("loader").remove();
                }
            })

        firebase.firestore().collection("pouzivatelia").doc(uid).collection("zaznamy")
            .get().then(snap => {
                let dataCount = snap.size;
                let size;
                
                if(dataCount%20 == 0){
                    size = (dataCount/20);
                }else if(dataCount%20 < 10){
                    size = (Math.round(dataCount / 20) + 1);
                }else{
                    size = (Math.round(dataCount / 20));
                }

                if(dataCount > 20){
                    document.getElementById("paginator").style.visibility = "visible";
                }

                if(page == size){
                    document.getElementById("next").style.visibility = "hidden";
                }else if(page == 1){
                    document.getElementById("previous").style.visibility = "hidden";
                }else{
                    document.getElementById("next").style.visibility = "visible";
                    document.getElementById("previous").style.visibility = "visible";
                }

                document.getElementById("next").addEventListener("click", e => {
                    if(page < (Math.round(dataCount / 3)+1)){
                        const table = document.getElementById("zaznamy");
                        let tableRows = table.getElementsByTagName("tr");
                        let rowCount = tableRows.length;
                        
                        for(let i=rowCount-1; i>0; i--){
                            table.removeChild(tableRows[i]);
                        }

                        firebase.firestore().collection("pouzivatelia").doc(uid).collection("zaznamy").orderBy(sortBy, sortType).startAfter(lastVisible).limit(20)
                            .get().then((snapshot) => {
                                snapshot.docs.forEach(doc => {
                                    retrieveData(doc);
                                })
                                lastVisible = snapshot.docs[snapshot.docs.length - 1];
                                firstVisible = snapshot.docs[0];
                                page++;
                                pageNum.innerText = page;
                        
                                if(page == size){
                                    document.getElementById("next").style.visibility = "hidden";
                                    document.getElementById("previous").style.visibility = "visible";
                                }else{
                                    document.getElementById("previous").style.visibility = "visible";
                                }
                            })
                    
                    }
                })
            });
                
                
            document.getElementById("previous").addEventListener("click", e => {
                if(page > 1){
                    const table = document.getElementById("zaznamy");
                    let tableRows = table.getElementsByTagName("tr");
                    let rowCount = tableRows.length;
                        
                    for(let i=rowCount-1; i>0; i--){
                        table.removeChild(tableRows[i]);
                    }
                        
                    firebase.firestore().collection("pouzivatelia").doc(uid).collection("zaznamy").orderBy(sortBy, sortType).endBefore(firstVisible).limitToLast(20)
                        .get().then((snapshot) => {
                            snapshot.docs.forEach(doc => {
                                retrieveData(doc);
                            })
                        
                            lastVisible = snapshot.docs[snapshot.docs.length - 1];
                            firstVisible = snapshot.docs[0];
                            page--;
                            pageNum.innerText = page;
                        
                            if(page == 1){
                                document.getElementById("previous").style.visibility = "hidden";
                                document.getElementById("next").style.visibility = "visible";
                            }else{
                                document.getElementById("next").style.visibility = "visible";
                            }
                        })
                }
            })
                    
                
            }else{
                firebase.firestore().collection("pouzivatelia").doc(uid).collection("zaznamy").orderBy(sortBy, sortType).orderBy(sortBy2, sortType).limit(20)
                    .get().then((snapshot) => {
                        if(snapshot.size > 0){
                            snapshot.docs.forEach(doc => {
                                retrieveData(doc);
                                if(loader != null){
                                    loader.remove();
                                }
                            })
                            lastVisible = snapshot.docs[snapshot.docs.length - 1];
            
                        }else{
                            const table = document.getElementById("zaznamy");
                            table.style.visibility = "hidden";
                            const sprava = document.getElementById("bezZaznamu");
                            sprava.classList.add("error");
                            sprava.innerText = "Nemáte žiadne záznamy";
                            document.getElementById("loader").remove();
                        }
                    })

                firebase.firestore().collection("pouzivatelia").doc(uid).collection("zaznamy")
                    .get().then(snap => {
                        let dataCount = snap.size;
                        let size;
                
                        if(dataCount%20 == 0){
                            size = (dataCount/20);
                        }else{
                            size = (Math.round(dataCount / 20) + 1);
                        }

                        if(dataCount > 20){
                            document.getElementById("paginator").style.visibility = "visible";
                        }

                        if(page == size){
                            document.getElementById("next").style.visibility = "hidden";
                        }else if(page == 1){
                            document.getElementById("previous").style.visibility = "hidden";
                        }else{
                            document.getElementById("next").style.visibility = "visible";
                            document.getElementById("previous").style.visibility = "visible";
                        }

                        document.getElementById("next").addEventListener("click", e => {
                            
                            if(page < (Math.round(dataCount / 3)+1)){
                                const table = document.getElementById("zaznamy");
                                let tableRows = table.getElementsByTagName("tr");
                                let rowCount = tableRows.length;
                        
                                for(let i=rowCount-1; i>0; i--){
                                    table.removeChild(tableRows[i]);
                                }

                                firebase.firestore().collection("pouzivatelia").doc(uid).collection("zaznamy").orderBy(sortBy, sortType).startAfter(lastVisible).limit(20)
                                    .get().then((snapshot) => {
                                        snapshot.docs.forEach(doc => {
                                            retrieveData(doc);
                                        })
                                        lastVisible = snapshot.docs[snapshot.docs.length - 1];
                                        firstVisible = snapshot.docs[0];
                                        page++;
                                        pageNum.innerText = page;
                        
                                        if(page == size){
                                            document.getElementById("next").style.visibility = "hidden";
                                            document.getElementById("previous").style.visibility = "visible";
                                        }else{
                                            document.getElementById("previous").style.visibility = "visible";
                                        }
                                    })
                    
                            }
                        })
                    });
                
                
                document.getElementById("previous").addEventListener("click", e => {
                    if(page > 1){
                        const table = document.getElementById("zaznamy");
                        let tableRows = table.getElementsByTagName("tr");
                        let rowCount = tableRows.length;
                        
                        for(let i=rowCount-1; i>0; i--){
                            table.removeChild(tableRows[i]);
                        }
                        
                        firebase.firestore().collection("pouzivatelia").doc(uid).collection("zaznamy").orderBy(sortBy, sortType).endBefore(firstVisible).limitToLast(20)
                            .get().then((snapshot) => {
                                snapshot.docs.forEach(doc => {
                                retrieveData(doc);
                                })
                        
                                lastVisible = snapshot.docs[snapshot.docs.length - 1];
                                firstVisible = snapshot.docs[0];
                                page--;
                                pageNum.innerText = page;
                        
                                if(page == 1){
                                    document.getElementById("previous").style.visibility = "hidden";
                                    document.getElementById("next").style.visibility = "visible";
                                }else{
                                    document.getElementById("next").style.visibility = "visible";
                                }
                            })
                    }
                })
            }
}



datumSort.addEventListener("click", e => {
    let urlParams = new URLSearchParams(window.location.search);
    if(urlParams == "" || (urlParams.get("sortType") === "DESC") || (!(urlParams.get("sortBy") === "datum"))){
        window.location.href = "tabulka.php?sortBy=datum&sortType=ASC";
    }else{
        window.location.href = "tabulka.php?sortBy=datum&sortType=DESC";
    }
})

casSort.addEventListener("click", e => {
    let urlParams = new URLSearchParams(window.location.search);
    if(urlParams == "" || (urlParams.get("sortType") === "DESC") || (!(urlParams.get("sortBy") === "cas"))){
        window.location.href = "tabulka.php?sortBy=cas&sortType=ASC";
    }else{
        window.location.href = "tabulka.php?sortBy=cas&sortType=DESC";
    }
})

casVoVodeSort.addEventListener("click", e => {
    let urlParams = new URLSearchParams(window.location.search);
    if(urlParams == "" || (urlParams.get("sortType") === "DESC") || (!(urlParams.get("sortBy") === "casVoVode"))){
        window.location.href = "tabulka.php?sortBy=casVoVode&sortType=ASC";
    }else{
        window.location.href = "tabulka.php?sortBy=casVoVode&sortType=DESC";
    }
})

vodaSort.addEventListener("click", e => {
    let urlParams = new URLSearchParams(window.location.search);
    if(urlParams == "" || (urlParams.get("sortType") === "DESC") || (!(urlParams.get("sortBy") === "voda"))){
        window.location.href = "tabulka.php?sortBy=voda&sortType=ASC";
    }else{
        window.location.href = "tabulka.php?sortBy=voda&sortType=DESC";
    }
})

vzduchSort.addEventListener("click", e => {
    let urlParams = new URLSearchParams(window.location.search);
    if(urlParams == "" || (urlParams.get("sortType") === "DESC") || (!(urlParams.get("sortBy") === "vzduch"))){
        window.location.href = "tabulka.php?sortBy=vzduch&sortType=ASC";
    }else{
        window.location.href = "tabulka.php?sortBy=vzduch&sortType=DESC";
    }
})