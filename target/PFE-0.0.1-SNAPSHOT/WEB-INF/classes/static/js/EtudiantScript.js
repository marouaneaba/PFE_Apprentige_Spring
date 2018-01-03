
    s = this;
    var tab = []
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
                
                var p = s.ligneSelected;

                if(s.ligneSelected == null){
                    s.code.appendChild(li);
                    console.log("1");
                }else if (s.ligneSelected.nextElementSibling != null && s.ligneSelected.nextElementSibling.localName === "ul"){

                    var ul = s.ligneSelected.nextElementSibling;
                    s.ligneSelected.nextElementSibling.insertBefore(li,ul.firstChild.nextElementSibling);
                    console.log("2");
                }else if( s.ligneSelected.localName == "li" && s.ligneSelected.innerHTML === ""){
                    s.ligneSelected.parentNode.insertBefore(li,s.ligneSelected.nextElementSibling);
                }else if (( s.ligneSelected.parentNode.id !="variable" && s.ligneSelected.firstChild != null && !s.ligneSelected.firstChild.data.startsWith("FIN")) ||
                 (s.ligneSelected.parentNode.id !="variable" && s.ligneSelected.firstChild.data != undefined && !s.ligneSelected.firstChild.data.startsWith("FIN"))){

                    s.ligneSelected.parentNode.insertBefore(li,s.ligneSelected.nextElementSibling);
                   
                    console.log("3");
                }else if(s.ligneSelected.firstChild.data != undefined && s.ligneSelected.firstChild.data.startsWith("FIN")){
                    console.log("4");
                   s.ligneSelected.parentNode.parentNode.appendChild(li); 
                }
                break;
            case "Supprimer Ligne":
            	
            	
            	if(s.ligneSelected.localName == "li" && s.ligneSelected.firstChild == null){
                    var supp = document.getElementById("s");
                        supp.parentNode.removeChild(supp);
                }else if(document.getElementById("s") === null){
                    alert("Vueillez selectionnez un ligne !!");
            	
                }else if(s.ligneSelected != null && s.ligneSelected.nextElementSibling != null && s.ligneSelected.nextElementSibling.localName === "ul"){
                    
                	var eeee = s.ligneSelected;
                    console.log(s.ligneSelected.localName);
                    //if(s.ligneSelected.innerHTML.startsWith("<li><if")){
                    if(s.ligneSelected.firstChild.localName == "if"){
                    	console.log("suup IF ELSE");
                        var ul =  s.ligneSelected.nextElementSibling;
                        var li2 = ul.nextElementSibling;
                        
                        var azerty = li2.innerHTML.replace("Sinon ","");
                        azerty = azerty.replace("elseif","if");
                         aerty = azerty.replace("\"","'");
                         console.log("eee : "+azerty);
                        li2.innerHTML = azerty;
                        
                        var balise = document.getElementsByTagName("li");
                        for(i = 0 ;i<balise.length;i++){;
                        	
                        	if(balise[i].localName == "li" && balise[i].innerHTML =="" && balise[i].nextSibling != null && balise[i].nextSibling.firstChild.localName =="elseif")
                        		balise[i].parentNode.removeChild(balise[i]);
                        	//console.log("byb name : "+balise[i].localName);
                        }

                    }
                    var supp = s.ligneSelected.nextElementSibling;
                        supp.parentNode.removeChild(supp);
                    var supp = document.getElementById("s");
                        supp.parentNode.removeChild(supp);

                   
                }else if(s.ligneSelected.firstChild.data == undefined || !s.ligneSelected.firstChild.data.startsWith("FIN")){

                    var supp = document.getElementById("s");
                        supp.parentNode.removeChild(supp);
                }else if( s.ligneSelected.localName == "li" && s.ligneSelected.firstChild == null){
                    var supp = document.getElementById("s");
                        supp.parentNode.removeChild(supp);
                }
                s.ligneSelected = null;
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
                
                if( tab.includes(variableSelectionner) ==false && variableSelectionner !== ""){

                	tab.push(variableSelectionner);
	                var listVariable = document.getElementById("NameVariable");
	                listVariable.innerHTML+="<option>"+variableSelectionner+"</option>";
	
	                var datalist1 = document.getElementById("variables");
	                datalist1.innerHTML+="<option value='"+variableSelectionner+"'/>";
	                
	                variable.innerHTML+="<li>"+variableSelectionner+"<span id='color'> EST_DU_TYPE </span>"+typeSelectionnez+"</li>";
                }else{
                	alert("Variable existe Où le Champ est Vide.!!");
                }
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
                console.log(nameSelectionnez);
                var valeur = document.getElementById("choix_variables").value;
                var test2 = document.getElementById("choix_variables");
                console.log(valeur);
                var arithmitique = document.getElementById("choix_con").value;
                
                console.log(arithmitique);  
                var Valarithmitique = document.getElementById("saisie").value;
                var test = document.getElementById("saisie");
                console.log(Valarithmitique);

                if(s.ligneSelected.firstChild == null ){ 
                    
                    if(document.getElementById("checkbox").checked == true){
                        ligneSelected.innerHTML="<Affectation var='"+nameSelectionnez+"' val1='"+valeur+"' arith='' val2='' >  "
                        +nameSelectionnez+" <span id='color'>Prend La Valeur</span>  "+valeur+"</Affectation>";
                    }else{

                        document.getElementById("saisie").disabled = false;
                        var Valarithmitique = document.getElementById("saisie").value;
                        ligneSelected.innerHTML="<Affectation var='"+nameSelectionnez+"' val1='"+valeur+"' arith='"+arithmitique+"' val2='"
                        +Valarithmitique+"' >  "
                        +nameSelectionnez+" <span id='color'>Prend La Valeur</span>  "+valeur+" "+arithmitique+" "+Valarithmitique+"</Affectation>";
                    }
                    

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
                    var arithmitique = document.getElementById("choix_con").value;
                    var valeur = document.getElementById("choix_variables").value;
                   

                    if(document.getElementById("s").firstChild != null && document.getElementById("s").firstChild.data==="FIN_SI"){
                        document.getElementById("s").removeChild(document.getElementById("s").firstChild);
                        
                        var balise = document.createElement("li");
                        balise.innerHTML = "<li><elseif val1='"+nameSelectionnez+"' arith='"+arithmitique+"' val2='"+valeur+"'><span id='color'>Sinon SI (</span> "
                        +nameSelectionnez+"  "+arithmitique+"  "+valeur+" <span id='color'>) ALors</span> </li><ul id='r'><li>DEBUT_SINON_SI</li><li></li><li>FIN_SI</li></ul></elseif>";
                        
                        
                        ligneSelected.innerText = "FIN_SI";
                        var variableSearch = ligneSelected.parentNode;
                        variableSearch.parentNode.insertBefore(balise,variableSearch.nextSibling);
                        balise.id = "";
                        //document.getElementById("s").pa.insertBefore(ul,document.getElementById("s").parentNode.parentNode.parentNode.nextSibling);
                    }else{
                        ligneSelected.innerHTML = "<li><if val1='"+nameSelectionnez+"' arith='"+arithmitique+"' val2='"+valeur+"'> <span id='color'>Si (</span> "
                        +nameSelectionnez+"  "+arithmitique+"  "+valeur+" <span id='color'>) Alors</span></li> <ul id='r'><li>DEBUT_SI</li><li></li><li>FIN_SI</li></ul></if>"
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
                    
                    ligneSelected.innerHTML = "<li><pour var='"+nameVariable+"' de ='"+de+"' jusqua='"+jusqua+
                    "'> <span id='color'>variable :</span> "+nameVariable+" <span id='color'>allant de</span> "+de
                    +" <span id='color'>jusqu'a</span> "+jusqua+" </li><ul id='r'><li>DEBUT_POUR</li><li></li><li>FIN_POUR</li></ul></pour>";
                }else{
                    alert("impossible");
                }
                break;

            case "Boucle Tant Que":
                console.log(event.target.text);
                s.dialog.close();
                fermuture();
                
                if(!document.getElementById("s").textContent.startsWith("FIN")){
                    var nameSelectionnez = variableTypeSelectionner.NameVariable.value;
                    var arithmitique = document.getElementById("choix_con").value;
                    var valeur = document.getElementById("choix_variables").value;

                    ligneSelected.innerHTML = "<li><tantque val1='"+nameSelectionnez+"' arith='"+arithmitique+"' val2='"+valeur+
                    "'><span id='color'>Tant Que (</span> "+nameSelectionnez+"  "+arithmitique+"  "+valeur+" <span id='color'>) faire </span></li><ul id='r'><li>DEBUT_TANT_QUE</li><li></li><li>FIN_TANT_QUE</li></ul></tantque>" ;
                }else{
                    alert("impossible");
                }
                break;
        }
        s.ligneSelected = null;
    }

    function Annuler(){
        dialog.close();
        fermuture();
    }

function selectioLigne(event){
                /*if(event.target.id != "variable")
                    console.log("hlkui");*/

        if(document.getElementById("s") && event.target.id != "color"){
            document.getElementById("s").removeAttribute("style");
            document.getElementById("s").id = "ch";
            console.log("here!!");
        }
        console.log(document.getElementById("s"));


        var o = event.target;
        
        if(event.target.id != "variable"  && event.target.id != "" && event.target.id != "color" && event.target.id!="code" ){
            
            event.target.style.background = "#A4A4A4";
            event.target.id = "s";
            console.log("id: "+event.target.id);
            console.log("polot");

        }
        
        console.log("z: "+event.target.id);
        var test = event.target;
        if(event.target.id == "" && event.target.localName =="li" && event.target.localName !=="ul" && event.target.id!="code"
            && !event.target.innerText.startsWith("DEBUT") ){
            console.log("marouane");
            event.target.style.background = "#A4A4A4";
            event.target.id = "s";
        }

        if(document.getElementById("s") != null && document.getElementById("s").localName !="ul" &&( event.target.childElementCount>1 || (document.getElementById("s") != null 
            && document.getElementById("s").innerHTML.startsWith("<span")))){
            console.log("here!!222");
            event.target.style.background="";
            var t = document.getElementById("s").id;
            console.log(t);
            if(document.getElementById("s").id != "color")
            document.getElementById("s").id = "ch";
        }
        s.ligneSelected = document.getElementById("s");

        if( document.getElementById("ch"))
            document.getElementById("ch").removeAttribute("style");

        if( document.getElementById("r"))
            document.getElementById("r").removeAttribute("style");

    
    }

    function fermuture(){

        document.getElementById("input1").value = "";
        document.getElementById("input2").value = "";
        document.getElementById("checkbox").value = "";
        document.getElementById("saisie").value = "";

        document.getElementById("checkbox").classList.remove('show');
        document.getElementById("choix_con").classList.remove('show');
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

        document.getElementById("button").classList.remove("show");
        
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

   

