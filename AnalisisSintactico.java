/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2junio;

/**
 *
 * @author LUIS QUIÑONEZ
 */
public class AnalisisSintactico {
    
    private AnalisisLexico a_lexico;
    private int posicion;
    private int id_lexema;

    
    public AnalisisSintactico(){
        //constructor vacio
    }
    
    public AnalisisSintactico(AnalisisLexico analizador_lexico) {
        this.a_lexico = analizador_lexico;
        this.posicion=0;
        this.id_lexema=analizador_lexico.getToken().get(this.posicion).getID();//obtener el id del token en la posicion x
    }
    

    
    
    
     /*
    [lenguaje]              = 0
    [-lenguaje]             = 100
    [[componentes lexicos]] = 1
    [[reservadas]]          = 2
    [[Gramatica: nombre]]   = 3
    asignacion '::='        = 10
    fin '$'                 = 11
    coma ','                = 12
    igual '='               = 13
    parentesisA '('         = 14
    parentesisC ')'         = 15
    llave A '{'             = 16
    llave C '}'             = 17
    OR '|'                  = 18
    AND '.'                 = 19
    * ? +                   = 20
    ID/terminal             = 30
    Caracter esp 'ch'       = 31
    Reservada "res"         = 32
    noTerminal <ID>         = 33 ya no existe
    epsilon                 = 34
    Terminales              = 35 
    No_terminales           = 36 
    Inicio                  = 37
    <                       = 38
    >                       = 39
    Producciones            = 40
    letra                   = 50
    numero                  = 51
    */
    
    
    
    
    public void parea(int id){
        if(this.id_lexema == id ){
            posicion++;
            if(posicion==this.a_lexico.getToken().size()){
                this.id_lexema = -1;
            }else{
                this.id_lexema= this.a_lexico.getToken().get(posicion).getID();
            }
        }else{
            System.out.println("#Error, funcion parea Se esperaba idToken:" + id +"se Obtuvo: "+a_lexico.getToken().get(posicion).getLexema()+"#");
        }
    }
    
    
    public void analizar(){
        id_lexema = a_lexico.getToken().get(posicion).getID();
        S();      
        if(posicion != a_lexico.getToken().size()){
          System.out.println("Ocurrio un error, se esperaba +, *, (: #" + a_lexico.getToken().get(posicion).getLexema()+"#");  
        }
        else{
            //hacer algo que me haga darme cuenta de que si funciono el trabajo
        }
    }
    
    private void S(){
        switch(id_lexema){
            case 0://id de etiqueta [lenguaje]
                parea(0);//[lenguaje]
                System.out.println("[lenguaje]");//debug
                cuerpo();//cuerpo
                parea(100);//[-lenguaje]
                System.out.println("[-lenguaje]");//debug
                break;
            default:
                System.out.println("Error, funcion S se esperaba [lenguaje]");//debug
        }
    }
    
    private void cuerpo(){
        switch(id_lexema){
            case 1://[[Componentes Lexicos]]
                parea(1);
                System.out.println("[[Componentes Lexicos]]");//debug
                cuerpoComponentes();
                parea(2);
                System.out.println("[[reservadas]]");//debug
                cuerpoReservadas();
                parea(3);//[Gramatica: nombre]]
                System.out.print(a_lexico.getToken().get(posicion).getLexema());//debug
                System.out.println(a_lexico.getToken().get(3).getLexema());//debug
                cuerpoGramatica();
                break;
            default:
                System.out.println("#Error, funcion cuerpo se esperaba [[Componentes Lexicos]]#");//debug
        }
    }
    
    private void cuerpoComponentes(){
        switch(id_lexema){
            case 30://identificador
                parea(30);//identificador
                System.out.print("identificador");//debug
                parea(10);
                System.out.print("::=");//debug
                ER();//expresion regular
                parea(11);//fin $
                System.out.println("$");
                cuerpoComponentesPR();
                
                break;
            default:
                System.out.println("#error,funcion cuerpoComponentes se esperaba identificador:"+a_lexico.getToken().get(id_lexema).getLexema()+"#");
        }
    }
    
    private void cuerpoComponentesPR(){
        switch(id_lexema){
            case 30:
                cuerpoComponentes();
                break;
            default:
                
        }
    }
    
    private void ER(){
        T();
        ERpr();
    }
    
    private void ERpr(){
        switch(id_lexema){
            case 18://OR
                parea(18);
                System.out.print("|");
                T();
                ERpr();
                break;
            default:
        }
    }
    
    private void T(){
        R();
        Tpr();
    }
    
    private void Tpr(){
        switch(id_lexema){
            case 19://AND
                parea(19);
                System.out.print(".");
                R();
                Tpr();
                break;
            default:
        }
    }
    
    private void R(){
        P();
        Rpr();
    }
    
    private void Rpr(){
        switch(id_lexema){
            case 20://* ? +
                parea(20);
                System.out.print(a_lexico.getToken().get(posicion-1).getLexema());//revisar
                Rpr();
                break;
            default:
        }
    }
    
    private void P(){
        switch(id_lexema){
            case 14:
                parea(14);
                System.out.print("(");//debug
                ER();
                parea(15);
                System.out.print(")");//debug
                break;
            case 50:
                parea(50);
                System.out.print("letra");//debug
                break;
            case 51:
                parea(51);
                System.out.print("numero");//debug
                break;
            case 31:
                parea(31);
                System.out.print("caracter");//debug
                break;
            default:
                System.out.println("#Error funcion P, se esperaba letra numero o caracter:"+a_lexico.getToken().get(id_lexema).getLexema()+"#");//debug
        }
    }
    
    private void cuerpoReservadas(){
        switch(id_lexema){
            case 30:
                parea(30);
                System.out.print("identificador");//debug
                parea(10);
                System.out.print("::=");//debug
                parea(32);
                System.out.print("reservada");//debug
                parea(11);
                System.out.println("$");//debug
                cuerpoReservadasPR();
                break;
            default:
                System.out.println("#error, funcion cuerpoReservada se esperaba identificador, se obtuvo:"+a_lexico.getToken().get(id_lexema).getLexema()+"#");//debug
        }
    }
    
    private void cuerpoReservadasPR(){
        switch(id_lexema){
            case 30:
                cuerpoReservadas();//identificador pero no lo consumo
                break;
            default:
        }
    }
    
    private void cuerpoGramatica(){
        switch(id_lexema){
            case 35:
                parea(35);//reservada terminales
                System.out.print("terminales");//debug
                parea(13);//simbolo =
                System.out.print("=");//debug
                parea(16);//llave que abre
                System.out.print("{");//debug
                Ter();
                parea(17);//llave que cierra
                System.out.print("}");//debug
                parea(36);//reservada no terminales
                System.out.print("no_terminales");//debug
                parea(13);//simbolo =
                System.out.print("=");//debug
                parea(16);//llave que abre
                System.out.print("{");//debug
                NT();
                parea(17);//llave que cierra
                System.out.print("}");//debug
                parea(37);//reservada inicio
                System.out.print("inicio");//debug
                parea(13);//simbolo =
                System.out.print("=");//debug
                parea(38);//simbolo <
                System.out.print("<");//debug
                parea(30);//identificador, terminal, no terminal
                System.out.print("no terminal");//debug
                parea(39);//simbolo >
                System.out.print(">");//debug
                parea(40);//reservada producciones
                System.out.print("producciones");
                parea(13);//simbolo =
                System.out.print("=");//debug
                parea(16);
                System.out.print("{");//debug
                Pro();
                parea(17);//llave que cierra
                System.out.print("}");//debug
                break;
            default:
                System.out.println("#error, cuerpo Gramatica Se esperaba la reservada terminales, se obtuvo:"+a_lexico.getToken().get(id_lexema).getLexema()+"#");//debug
        }
    }
    
    private void Ter(){
        A();
        TerPR();
    }
    
    private void TerPR(){
        switch(id_lexema){
            case 12:
                parea(12);
                System.out.print(",");//debug
                A();
                TerPR();
                break;
            default:
                
        }
    }
    
    private void A(){
        switch(id_lexema){
            case 30:
                parea(30);//terminal, no terminal, identificador
                break;
            default:
                System.out.print("#Error funcion A, se esperaba un terminal, se obtuvo:"+a_lexico.getToken().get(posicion).getLexema()+"#");//debug
        }
    }
    
    private void NT(){
        B();
        NTpr();
    }
    
    private void NTpr(){
        switch(id_lexema){
            case 12:
                parea(12);//simbolo ,
                System.out.print(",");//debug
                B();
                NTpr();
                break;
            default:
                
        }
    }
    
    private void B(){
        switch(id_lexema){
            case 30:
                parea(30);//terminal, no terminal, identificador
                break;
            default:
                System.out.print("#Error funcion B, se esperaba un no terminal, se obtuvo:"+a_lexico.getToken().get(id_lexema).getLexema()+"#");//debug
        }
    }
    
    private void Pro(){
        switch(id_lexema){
            case 38:
                parea(38);//simbolo <
                System.out.print("<");//debug
                parea(30);//terminal, no terminal, identificador
                System.out.print("noterminal");
                parea(39);//simbolo >
                System.out.print(">");//debug
                parea(10);//asignacion ::=
                System.out.print("::=");//debug
                C();
                parea(11);//fin $
                System.out.println("$");//debug
                ProPR();
                break;
            default:
                System.out.println("#error funcion pro, se esperaba un no terminal, se obtuvo:"+a_lexico.getToken().get(id_lexema).getLexema()+"#");//debug
                        
        }
    }
    
    private void ProPR(){
        switch(id_lexema){
            case 38://leo el lexema pero no lo consumo
                Pro();//esto es para asegurarme que por lo menos venga una produccion
                break;
            default:
                
        }
    }
    
    private void C(){
        D();
        Cpr();
    }
    
    private void Cpr(){
        switch(id_lexema){
            case 18:
                parea(18);//OR
                System.out.print("|");
                D();
                Cpr();
                break;
            case 30://cuidado
                
                break;
            default:
        }
    }
    
    private void D(){
        switch(id_lexema){
            case 38://para un no terminal
                parea(38);//simbolo <
                System.out.print("<");//debug
                parea(30);//terminal, no terminal, identificador
                System.out.print("noterminal");//debug
                parea(39);//simbolo >
                System.out.print(">");//debug                
                break;
            case 30:
                parea(30);
                System.out.print("terminal");//debug
                break;
            case 34:
                parea(34);
                System.out.print("epsilon");//debug
            default:
                
        }
    }
}
