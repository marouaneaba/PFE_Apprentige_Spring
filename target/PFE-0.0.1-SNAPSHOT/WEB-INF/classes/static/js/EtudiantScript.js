
    s = this;
    this.traitement = null;
    this.buttonBalise = document.getElementById("button_algo");
    
    this.dialog = document.getElementById("mydialog");
    this.variable = document.getElementById("variable");
    this.code = document.getElementById("code");
    this.selectionDialog = document.getElementById("sel");


    this.variable.addEventListener("click",selectioLigne);
    this.code.addEventListener("click",selectioLigne);
    this.ligneSelected = null;
    this.ulCourant = document.getElementById("s");


    this.buttonBalise.addEventListener("click",function(event){
        s.variable.addEventListener("click",selectioLigne);
        s.traitement = event.target.text;
        //console.log("click");
        //valider();
        
        switch(event.target.text){
            case "Ajouter Ligne":
                var li = document.createElement("li");
                li.id= "ch";
                if(s.ligneSelected == null){
                    s.code.appendChild(li);
                }else if (s.ligneSelected.firstChild === null || !s.ligneSelected.firstChild.data.startsWith("FIN")){

                    s.ligneSelected.parentNode.insertBefore(li,s.ligneSelected.nextElementSibling);
                   
                    console.log("im");
                }else if(!s.ligneSelected.firstChild.data.startsWith("FIN")){
                   s.ligneSelected.parentNode.parentNode.appendChild(li); 
                }
                
                break;
            case "Supprimer Ligne":
                if(document.getElementById("s") === null)
                    alert("Vueillez selectionnez un ligne !!");
                else{
                    var result = confirm("étes vous sùr de supprimer se ligne!!!");
                    if(result === true){
                        if(document.getElementById("s")){
                            var supp = document.getElementById("s");
                            supp.parentNode.removeChild(supp);
                        }else alert("Veuillez selectionner un ligne à supprimer !!");
                    }
                }
                break;
            case "Declarer Variable":
            	
                document.getElementById("typeVariable").classList.toggle("show");
                document.getElementById("typeVar").classList.toggle('show');
                document.getElementById("nameVariable").classList.toggle('show');
                document.getElementById("input1").classList.toggle("show");
                
                ShowMessage();
                break;
            case "Lire Variable":
                document.getElementById("NameVariable").classList.toggle('show');
                if(s.ligneSelected === null || s.ligneSelected.parentNode.id === "variable" ){
                    alert("Veuillez selectionnez un ligne dans Algorithmezzzmmm!! ");
                }else{
                    
                    ShowMessage();
                }
                break;
            case "Aff valeur a une Variable":
                document.getElementById("NameVariable").classList.toggle("show");
                document.getElementById("choix_variables").classList.toggle("show");
                document.getElementById("choix_con").classList.toggle("show");
                document.getElementById("saisie").classList.toggle("show");
                document.getElementById("fleche").classList.toggle("show");
                document.getElementById("checkbox").classList.toggle("show");

                var checkbox = document.getElementById("checkbox");
                checkbox.checked = true;
                document.getElementById("choix_con").disabled=true;
                document.getElementById("saisie").disabled=true;

                checkbox.addEventListener("change",function(event){
                    if(this.checked) {
                        document.getElementById("choix_con").disabled=true;
                        document.getElementById("saisie").disabled=true;
                    } else {
                        document.getElementById("choix_con").disabled=false;
                        document.getElementById("saisie").disabled=false;
                    }
                });

                if(s.ligneSelected === null || s.ligneSelected.parentNode.id === "variable" ){
                    alert("Veuillez selectionnez un ligne dans Algorithmezzz!! ");
                }else{
                    
                    ShowMessage();
                }
                break;
            case "Afficher Variable":
                console.log(event.target.text);
                document.getElementById("NameVariable").classList.toggle('show');
                if(s.ligneSelected === null || s.ligneSelected.parentNode.id === "variable" ){
                    alert("Veuillez selectionnez un ligne dans Algorithmezzz!! ");
                }else{
                    
                    ShowMessage();
                }
                break;
            case "Condition Si":
                console.log(event.target.text);

                
                
                document.getElementById("NameVariable").classList.toggle('show');
                document.getElementById("typeConditionSi").classList.toggle('show');  
                document.getElementById("choix_variables").classList.toggle('show'); 
                document.getElementById("si").classList.toggle('show'); 
                document.getElementById("alors").classList.toggle('show'); 

                
                if(s.ligneSelected === null || s.ligneSelected.parentNode.id === "variable" ){
                    alert("Veuillez selectionnez un ligne dans Algorithmezzz!! ");
                }else{
                    
                    ShowMessage();
                }
                break;
            case "Sinon Si":
                console.log(event.target.text);
                // TODO //
                break;
            case "Boucle Pour":
                console.log(event.target.text);
                document.getElementById("NameVariable").classList.toggle("show");
                document.getElementById("input1").classList.toggle('show');
                document.getElementById("input2").classList.toggle("show");
                document.getElementById("de").classList.toggle("show");
                document.getElementById("jusqu").classList.toggle("show");
                if(s.ligneSelected === null || s.ligneSelected.parentNode.id === "variable" ){
                    alert("Veuillez selectionnez un ligne dans Algorithmezzz!! ");
                }else{
                    
                    ShowMessage();
                }
                break;
            case "Boucle Tant Que":
                console.log(event.target.text);
                document.getElementById("NameVariable").classList.toggle("show");
                document.getElementById("typeConditionSi").classList.toggle("show");
                document.getElementById("choix_variables").classList.toggle("show");
                document.getElementById("tantque").classList.toggle("show");
                document.getElementById("alors").classList.toggle("show");

                if(s.ligneSelected === null || s.ligneSelected.parentNode.id === "variable" ){
                    alert("Veuillez selectionnez un ligne dans Algorithmezzz!! ");
                }else{
                    
                    ShowMessage();
                }
                break;
        }
    });

    

   function valider(variableTypeSelectionner,variableSelectionner){
        
        switch(s.traitement){
            case "Declarer Variable":
                var typeSelectionnez = variableTypeSelectionner.typeVariable.value;
                fermuture();
                s.dialog.close();

                var span2 = document.createElement("span");
                span2.id="color";
                span2.appendChild(document.createTextNode(" EST_DU_TYPE "));

                var li = document.createElement("li");
                li.id = "ch";
                var span1 = document.createElement("span");
                span1.appendChild(document.createTextNode(variableSelectionner));
                li.appendChild(span1);
                li.appendChild(span2);
                var span3 = document.createElement("span");
                span3.appendChild(document.createTextNode(typeSelectionnez));
                li.appendChild(span3);
                variable.appendChild(li);
                break;

            case "Lire Variable":
                s.dialog.close();
                fermuture();

                var nameSelectionnez = variableTypeSelectionner.NameVariable.value;
                if(s.ligneSelected === null || s.ligneSelected.parentNode.id === "variable" ){
                    alert("Veuillez selectionnez un ligne dans Algorithmezzz!! ");
                }else if(ligneSelected.firstChild === null ){

                    ligneSelected.innerHTML="<Lire var='"+nameSelectionnez+"'><span id='color'>Lire</span>  "+nameSelectionnez+" </Lire>";

                }else if(ligneSelected.firstChild != null ){
                    ligneSelected.firstChild.data = "LIREe "+nameSelectionnez;
                }
                break;
            case "Aff valeur a une Variable":
                s.dialog.close();
                fermuture();

                var nameSelectionnez = variableTypeSelectionner.NameVariable.value;
                var valeur = document.getElementById("choix_variables").value;
                var arithmitique = "inf";//document.getElementById("choix_con").value;
                var Valarithmitique = document.getElementById("saisie").value;

                if(s.ligneSelected.firstChild == null ){ 
                    
                    ligneSelected.innerHTML="<Affectation var='"+nameSelectionnez+"' val1='"+valeur+"' arith='"+arithmitique+"' val2='"
                    +Valarithmitique+"' >  "+
                    +nameSelectionnez+" <span id='color'>Prend La Valeur</span>  "+valeur+" "+arithmitique+" "+Valarithmitique+"</Affectation>";

                }else if(s.ligneSelected.firstChild != null ){
                    s.ligneSelected.firstChild.data = String(variableSelectionner+" PERND_LA_VALEUR "+msg);
                }
                break;
            case "Afficher Variable":
                s.dialog.close();
                fermuture();
                var nameSelectionnez = variableTypeSelectionner.NameVariable.value;

                if(ligneSelected.firstChild === null ){
                    
                    ligneSelected.innerHTML="<Afficher var='"+nameSelectionnez+"'><span id='color'>Afficher</span>  "+nameSelectionnez+" </Afficher>";
                }else if(ligneSelected.firstChild != null ){
                    ligneSelected.firstChild.data = "AFFICHER "+nameSelectionnez;
                }
                break;
            case "Condition Si":
                s.dialog.close();
                fermuture();

                if(!document.getElementById("s").textContent.startsWith("FIN_P") && !document.getElementById("s").textContent.startsWith("FIN_T")){

                    
                  

                    var nameSelectionnez = variableTypeSelectionner.NameVariable.value;
                    var arithmitique = "inf";//document.getElementById("choix_con").value;
                    var valeur = document.getElementById("choix_variables").value;
                   

                    if(document.getElementById("s").firstChild != null && document.getElementById("s").firstChild.data==="FIN_SI"){
                        document.getElementById("s").removeChild(document.getElementById("s").firstChild);
                        
                        var balise = document.createElement("li");
                        balise.innerHTML = "<elseif val1='"+nameSelectionnez+"' arith='"+arithmitique+"' val2='"+valeur+"'><span id='color'>Sinon SI (</span> "
                        +nameSelectionnez+"  "+arithmitique+"  "+valeur+" <span id='color'>) ALors</span> <ul><li>DEBUT_SINON_SI</li><li></li><li>FIN_SI</li></ul></elseif>";
                        
                        
                        ligneSelected.innerText = "FIN_SI";
                        var variableSearch = ligneSelected.parentNode.parentNode.parentNode;
                        variableSearch.parentNode.insertBefore(balise,variableSearch.nextSibling);
                        
                        //document.getElementById("s").pa.insertBefore(ul,document.getElementById("s").parentNode.parentNode.parentNode.nextSibling);
                    }else{
                        ligneSelected.innerHTML = "<if val1='"+nameSelectionnez+"' arith='"+arithmitique+"' val2='"+valeur+"'> <span id='color'>Si (</span> "
                        +nameSelectionnez+"  "+arithmitique+"  "+valeur+" <span id='color'>) Alors</span> <ul><li>DEBUT_SI</li><li></li><li>FIN_SI</li></ul></if>";
                    }
                }
                break;

            case "Boucle Pour":
                console.log(event.target.text);
                s.dialog.close();
                fermuture();

                if(!document.getElementById("s").textContent.startsWith("FIN")){
                    var nameVariable = document.getElementById("NameVariable").value;
                    var de = document.getElementById("input1").value;                    
                    var jusqua = document.getElementById("input2").value;                    
                    
                    ligneSelected.innerHTML = "<pour var='"+nameVariable+"' de ='"+de+"' jusqua='"+jusqua+
                    "'> <span id='color'>variable :</span> "+nameVariable+" <span id='color'>allant de</span> "+de
                    +" <span id='color'>jusqu'a</span> "+jusqua+" <ul><li>DEBUT_POUR</li><li></li><li>FIN_POUR</li></ul></pour>";
                }
                break;

            case "Boucle Tant Que":
                console.log(event.target.text);
                s.dialog.close();
                fermuture();
                
                if(!document.getElementById("s").textContent.startsWith("FIN")){
                    var nameSelectionnez = variableTypeSelectionner.NameVariable.value;
                    var arithmitique = "inf";//document.getElementById("choix_con").value;
                    var valeur = document.getElementById("choix_variables").value;

                    ligneSelected.innerHTML = "<tantque val1='"+nameSelectionnez+"' arith='"+arithmitique+"' val2='"+valeur+
                    "'><span id='color'>Tant Que (</span> "+nameSelectionnez+"  "+arithmitique+"  "+valeur+" <span id='color'>) faire </span><ul><li>DEBUT_TANT_QUE</li><li></li><li>FIN_TANT_QUE</li></ul></tantque>" ;
                }
                break;
        }
    }

    function Annuler(){
        dialog.close();
        fermuture();
    }

    function selectioLigne(event){
        
        

        /*var nomExercice = encodeURIComponent(document.getElementById("nameExercice").value);
                console.log(nomExercice);
        var exercice = encodeURIComponent(document.getElementById("enoncer_exo").value);
                console.log(exercice);
        var variable = encodeURIComponent(document.getElementById("variable").innerHTML);
                console.log(variable);
        var code = encodeURIComponent(document.getElementById("code").innerHTML);
                console.log(code);*/


        if(document.getElementById("s")){
            document.getElementById("s").removeAttribute("style");
            document.getElementById("s").id = "ch";
        }
        /*if(event.target.id==="variable" || event.target.id === "code"){
            event.target.style.background="";
        }else*/ if(event.target.id != "variable"  ){
            event.target.style.background = "#A4A4A4";
            event.target.id = "s";
        }

        /*var t  = document.getElementById("s");
        
            console.log("1 : "+t.localName);*/

        if(event.target.childElementCount>1 || document.getElementById("s").localName ==="span"){
            event.target.style.background="";
            document.getElementById("s").id = "ch";
        }
        s.ligneSelected = document.getElementById("s");
    }

    function fermuture(){
        document.getElementById("typeVariable").classList.remove('show');
        document.getElementById("typeConditionSi").classList.remove('show');
        document.getElementById("typeVar").classList.remove("show");
        document.getElementById("choix_variables").classList.remove('show');
        document.getElementById("input1").classList.remove('show');
        document.getElementById("input2").classList.remove('show');
        document.getElementById("saisie").classList.remove('show');
        document.getElementById("si").classList.remove('show');
        document.getElementById("alors").classList.remove('show');
        document.getElementById("de").classList.remove('show');
        document.getElementById("jusqu").classList.remove('show');
        document.getElementById("tantque").classList.remove("show");
        
        document.getElementById("fleche").classList.remove("show");
        document.getElementById("NameVariable").classList.remove("show"); 
        document.getElementById("nameVariable").classList.remove("show");
        
    }

    function ShowMessage(){
        dialog.showModal();
        /*while(s.selectionDialog.childElementCount!=0){
            //supprimer tout les enfant de select(ajout).
            var el = document.getElementById("option");
            el.parentNode.removeChild(el);
            console.log(s.selectionDialog.childElementCount);
        }
        //console.log(variable.childElementCount);
        for(var i=0;i<s.variable.childElementCount;i++){
            var option = document.createElement("option");
            option.id = "option";
            s.selectionDialog.appendChild(option);
            var arraySt = s.variable.children[i].innerHTML.split(" ");
            //console.log(document.createTextNode(variable.children[0].innerHTML));
            option.appendChild(document.createTextNode(arraySt[0]));
        }*/
    }


