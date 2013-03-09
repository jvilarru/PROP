/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upc.prop.presentacio;

/**
 *
 * @author maria
 */
public class Ajuda extends javax.swing.JDialog {

    /**
     * Creates new form Ajuda
     */
    public Ajuda() {
        super();
        initComponents();
        
        setModal(true);
        
        String help = "<html><b><u>MANUAL D'USUARI</u><br><br><u>Index:</u><br><br>0. Com començar<br><br>"
                + "1. General<br>1.1. Obrir<br>1.2. Guardar<br>1.3. Sortir<br>1.4. Configurar Alfabet<br><br>"
                + "2. Dades<br>2.1. Pacients<br>2.1.1. Nou Pacient<br>2.1.2. Consultar Pacient<br>2.1.3. Modificar Pacient<br>"
                + "2.1.4. Eliminar Pacient<br><br>2.2. Malalties<br>2.2.1. Nova Malaltia<br>2.2.2. Consultar Malaltia<br>"
                + "2.2.3. Modificar Malaltia<br>2.2.4. Eliminar Malalia<br><br>2.3. Símptomes<br>2.3.1. Nou Símptoma<br>"
                + "2.3.2. Consultar Símptoma<br>2.3.3. Modificar Símptoma<br>2.3.4. Eliminar Símptoma<br><br>"
                + "2.4. Seqüències<br>2.4.1. Nova Seqüència<br>2.4.2. Consultar Seqüència<br>2.4.3. Eliminar Seqüència<br><br>"
                + "3. Estudis<br>3.1. Nous Patrons<br>3.2. Patrons Coneguts<br>3.3. Veure Resultats<br>"
                + "3.3.1. Resultat Gràfic<br>3.3.2. Resultat Estadístic<br>3.3.3. Conclusions Automàtiques</b><br><br>"
                + "-----------------------------------------------------------------------<br><br>"
                + "<b><u>0. Com començar:</u></b><br><br>"
                + "Crear pacients, malalties, símptomes i seqüències // Carregar entorn anterior<br>"
                + "Buscar nous patrons // Buscar patrons coneguts<br>"
                + "Veure resultats i acceptar les associacions de les conclusions automàtiques<br>"
                + "Guardar<br><br><br>"
                + "<b><u>1. General:</u></b><br><br>"
                + "<b>1.1. Obrir</b><br>"
                + "pide si quieres guardar los archivos actuales antes de cargar nuevos: Sí, guardar(todos); "
                + "No, descartar (todos); Cancelar, no abrir."
                + "si existe la carpeta de hoy, se abre en esta, si no existe te muestra la carpeta Dades, "
                + "que es donde estan las carpetas de fechaspor defecto se muestra el Obrir dependencias "
                + "marcado, si se escoge un archivo con esta opcion, se abrira todos los de mismo nombre a "
                + "la vez para abrir el entorno completo si se desmarca, se abrira el archivo individualmente. "
                + "esta opcion no es recomendable pues pueden darse errores de asociaciones al cargar archivos "
                + "que no se corresponden<br><br><br>"
                + "<b>1.2. Guardar</b><br>"
                + "se guardan todos, por defecto en la carpeta de hoy (si no existe se crea) los archivos "
                + "vacíos no se guardan (si no hay malalties etc)<br><br><br>"
                + "<b>1.3. Sortir</b><br>"
                + "pide si quieres guardar los archivos actuales antes de salir: Si, guardar; No, descartar; "
                + "Cancelar, no salir.<br><br><br>"
                + "<b><u>2. Dades:</u></b><br><br>"
                + "Gestionar dades: pacients, malalties, símptomes, seqüències i associacions entre ells.<br>"
                + "<b>2.1. Pacients:</b><br>"
                + "<u>2.1.1. Nou Pacient:</u><br>"
                + "Pacients, botó + de la barra de dalt.<br>Camps obligatoris: nif, data naixement, sexe i dna.<br>"
                + "Formats: nif 8 digits + 1 lletra; data naixement: dd/mm/aaaa, data anterior a la d'avui.<br>"
                + "Introduir dna: escriure dna al camp autocompletable. Si es troben seqüències que comencen igual "
                + "que les lletres introduides, apareixeran en una llista sota el camp<br>"
                + "es pot seguir escrivint o escollir un element de la llista si es desitja.<br>"
                + "si la seq no existeix, s'escriu tota i en acceptar, es crearà automàticament com a nova seq.<br>"
                + "Associar malalties i/o simptomes: clic en + del quadre corresponent i de la llista que es mostra, "
                + "fer clic en les que volem associar i fer Afegir, i en acabat fer Acceptar.<br>"
                + "Per desassociar malalties i/o símptomes, seleccionar element del llistat i fer clic en - del "
                + "quadre corresponent.<br>"
                + "Cancelar descarta els canvis. Acceptar mostra pacient a la llista de l'esquerra i consulta "
                + "en panell central.<br>"
                + "<u>2.1.2. Consultar Pacient:</u><br>"
                + "Pacients, seleccionar pacient de la llista de l'esquerra i es mostra consulta (no editable) "
                + "al panell central.<br>"
                + "Es veurà en l'historial totes les seqs d'adn que ha tingut i la data en què s'han associat.<br>"
                + "L'historial és de només consulta, no es pot modificar ni eliminar.<br>"
                + "<u>2.1.3. Modificar Pacient:</u><br>"
                + "Pacients, seleccionar pacient de la llista de l'esquerra i clic a botó llapis de la barra de dalt.<br>"
                + "Dni, sexe i data naixement no es poden editar. Si es volen canviar, es crea nou pacient.<br>"
                + "Dna es pot canviar, i s'afegirà el nou com una nova entrada a l'historial del pacient.<br>"
                + "Associar malalties i/o simptomes: clic en + del quadre corresponent i de la llista que es mostra, "
                + "fer clic en les que volem associar i fer Afegir, i en acabat fer Acceptar.<br>"
                + "Per desassociar malalties i/o símptomes, seleccionar element del llistat i fer clic en - del "
                + "quadre corresponent.<br>"
                + "Cancelar descarta els canvis i torna a mostrar consulta amb dades anteriors. "
                + "Acceptar guarda canvis i mostra consulta amb noves dades.<br>"
                + "<u>2.1.4. Eliminar Pacient:</u><br>"
                + "Pacients, seleccionar pacient de la llista de l'esquerra i clic a botó - de la barra de dalt.<br>"
                + "Confirmar si es vol eliminar o no.<br><br>"
                + "<b>2.2. Malalties:</b><br>"
                + "<u>2.2.1. Nova Malaltia:</u><br>"
                + "Malalties, clic en botó + de la barra superior.<br>"
                + "Camps obligatoris: nom.<br>"
                + "Associar pacients, seqüències i/o simptomes: clic en + del quadre corresponent i de la llista "
                + "que es mostra, fer clic en les que volem associar i fer Afegir, i en acabat fer Acceptar.<br>"
                + "Per desassociar pacients, seqüències i/o símptomes, seleccionar element del llistat i fer "
                + "clic en - del quadre corresponent.<br>"
                + "Cancelar descarta els canvis. Acceptar mostra malaltia a la llista de l'esquerra i consulta en panell central.<br>"
                + "<u>2.2.2. Consultar Malaltia:</u><br>"
                + "Malalties, seleccionar malaltia de la llista de l'esquerra i es mostra consulta (no editable) "
                + "al panell central.<br>"
                + "<u>2.2.3. Modificar Malaltia:</u><br>"
                + "Malalties, seleccionar malaltia de la llista de l'esquerra i clic a botó llapis de la barra de dalt.<br>"
                + "El nom no es pot editar. Si es vol canviar, es crea nova malaltia.<br>"
                + "Associar pacients, seqüències i/o simptomes: clic en + del quadre corresponent i de la llista que es "
                + "mostra, fer clic en les que volem associar i fer Afegir, i en acabat fer Acceptar.<br>"
                + "Per desassociar pacients, seqüències i/o símptomes, seleccionar element del llistat i fer clic "
                + "en - del quadre corresponent.<br>"
                + "Cancelar descarta els canvis i torna a mostrar consulta amb dades anteriors. "
                + "Acceptar guarda canvis i mostra consulta amb noves dades.<br>"
                + "<u>2.2.4. Eliminar Malaltia</u><br>"
                + "Malalties, seleccionar malaltia de la llista de l'esquerra i clic a botó - de la barra de dalt.<br>"
                + "Confirmar si es vol eliminar o no.<br><br>"
                + "<b>2.3. Símptomes:</b><br>"
                + "<u>2.3.1. Nou Símptoma:</u><br>"
                + "Simptomes, clic en botó + de la barra superior.<br>"
                + "Camps obligatoris: nom.<br>"
                + "Associar pacients i/o malalties: clic en + del quadre corresponent i de la llista "
                + "que es mostra, fer clic en les que volem associar i fer Afegir, i en acabat fer Acceptar.<br>"
                + "Per desassociar pacients i/o malalties, seleccionar element del llistat i fer clic "
                + "en - del quadre corresponent.<br>"
                + "Cancelar descarta els canvis. Acceptar mostra símptoma a la llista de l'esquerra i consulta "
                + "en panell central.<br>"
                + "<u>2.3.2. Consultar Símptoma:</u><br>"
                + "Símptomes, seleccionar símptoma de la llista de l'esquerra i es mostra consulta (no editable) "
                + "al panell central.<br>"
                + "<u>2.3.3. Modificar Símptoma:</u><br>"
                + "Símptomes, seleccionar símptoma de la llista de l'esquerra i clic a botó llapis de la barra de dalt.<br>"
                + "El nom no es pot editar. Si es vol canviar, es crea nou símptoma.<br>"
                + "Associar pacients i/o malalties: clic en + del quadre corresponent i de la llista que es mostra, "
                + "fer clic en les que volem associar i fer Afegir, i en acabat fer Acceptar.<br>"
                + "Per desassociar pacients i/o malalties, seleccionar element del llistat i fer clic "
                + "en - del quadre corresponent.<br>"
                + "Cancelar descarta els canvis i torna a mostrar consulta amb dades anteriors. Acceptar guarda canvis"
                + " i mostra consulta amb noves dades.<br>"
                + "<u>2.3.4. Eliminar Símptoma:</u><br>"
                + "Símptomes, seleccionar símptoma de la llista de l'esquerra i clic a botó - de la barra de dalt.<br>"
                + "Confirmar si es vol eliminar o no.<br><br>"
                + "<b>2.4. Seqüències:</b><br>"
                + "<u>2.4.1. Nova Seqüència:</u><br>"
                + "Seqüències, clic en botó + de la barra superior.<br>"
                + "Camps obligatoris: Seqüència. Les seqüències no es poden modificar.<br>"
                + "Cancelar descarta els canvis. Acceptar mostra seqüència a la llista de l'esquerra i consulta en "
                + "panell central.<br>"
                + "<u>2.4.2. Consultar Seqüència:</u><br>"
                + "Seqüències, seleccionar seqüència de la llista de l'esquerra i es mostra consulta (no editable) "
                + "al panell central.<br>"
                + "<u>2.4.3. Eliminar Seqüència:</u><br>"
                + "Seqüències, seleccionar seqüència de la llista de l'esquerra i clic a botó - de la barra de dalt.<br>"
                + "Confirmar si es vol eliminar o no.<br><br><br>"
                + "<b><u>3. Estudis:</u></b><br>"
                + "<b>3.1. Buscar Nous Patrons:</b><br>"
                + "Tots els camps són opcionals. Si no s'omplen, es prenen els valors per defecte.<br>"
                + "<u>Formats:</u><br>"
                + "EdatInici, EdatFi, Q, LongitudMínima, LongitudMàxima són enters positius; "
                + "Error és un double positiu. DniInici i DniFi són 8 dígits.<br>"
                + "<u>Filtres:</u><br>"
                + "Per edat: rang d'edat entre EdatInici i EdatFi. El valor per defecte d'EdatInici és l'edat "
                + "menor del conjunt de pacients actual.<br>"
                + "El valor per defecte d'EdatFi és l'edat major del conjunt de pacients actual.<br>"
                + "Exemple: filtrar tots els pacients majors de 35 anys: EdatInici = 35, EdatFi = .<br>"
                + "Per Dni: rang de dni entre DniInici i DniFi. El valor per defecte de DniInici és el "
                + "dni menor del conjunt de pacients actual.<br>"
                + "El valor per defecte de DniFi és el dni major del conjunt de pacients actual.<br>"
                + "Exemple: no filtrar per dni: DniInici = , DniFi = .<br>"
                + "Per Sexe: filtrar per sexe (M o F) o no filtrar (tots).<br>"
                + "Per Símptomes: Seleccionar en llista símptomes (botó +, seleccionar, afegir i acceptar), "
                + "filtra els pacients que presentin els símptomes seleccionats.<br>"
                + "Per Malalties: Seleccionar en llista malalties (botó +, seleccionar, afegir i acceptar), "
                + "filtra els pacients que presentin les malalties seleccionades.<br>"
                + "Escollir pacients manualment: Seleccionar en llista pacients (botó +, seleccionar, afegir i acceptar), "
                + "filtra els pacients escollits.<br>"
                + "<u>Parametres:</u><br>"
                + "Q: enter positiu. Indica el número mínim de seqüències on ha d'aparèixer un patró per a ser "
                + "considerat vàlid i presentar-se als resultats.<br>"
                + "Error: real positiu entre 0 i 100 (%). Indica l'error màxim d'error que pot presentar un patró "
                + "respecte al seu match a una seqüència per a ser considerat vàlid.<br>"
                + "Longitud Mínima: enter positiu. Indica la longitud mínima en nucleòtids que ha de presentar "
                + "un patró per a ser considerat vàlid.<br>"
                + "Longitud Màxima: enter positiu. Indica la longitud màxima en nucleòtids que ha de presentar "
                + "un patró per a ser considerat vàlid.<br>"
                + "Paralel: Seleccionar si es vol executar l'estudi en paralel, aprofitant els nuclis de procés "
                + "de l'ordinador al màxim.<br>"
                + "Ins&Del: Seleccionar si es volen tenir en compte Insertions i Deletions a més a més de Mutations "
                + "en la cerca de patrons.<br><br>"
                + "<b>3.2. Buscar Patrons Coneguts:</b><br>"
                + "Tots els camps són opcionals. Si no s'omplen, es prenen els valors per defecte.<br>"
                + "<u>Formats:</u><br>"
                + "EdatInici, EdatFi, Q, LongitudMínima, LongitudMàxima són enters positius; Error és un double positiu. "
                + "DniInici i DniFi són 8 dígits.<br>"
                + "Patrons a buscar:<br>"
                + "Seleccionar en llista de Patrons (botó +, seleccionar, afegir i acceptar) les sequencies ja conegudes "
                + "del sistema per a buscarles en les sequencies de la mostra.<br>"
                + "Si no se n'escull cap, l'estudi serà igual a una cerca de Nous Patrons.<br>"
                + "<u>Filtres:</u><br>"
                + "Per edat: rang d'edat entre EdatInici i EdatFi. El valor per defecte d'EdatInici és l'edat "
                + "menor del conjunt de pacients actual.<br>"
                + "El valor per defecte d'EdatFi és l'edat major del conjunt de pacients actual.<br>"
                + "Exemple: filtrar tots els pacients majors de 35 anys: EdatInici = 35, EdatFi = .<br>"
                + "Per Dni: rang de dni entre DniInici i DniFi. El valor per defecte de DniInici és el dni menor "
                + "del conjunt de pacients actual.<br>"
                + "El valor per defecte de DniFi és el dni major del conjunt de pacients actual.<br>"
                + "Exemple: no filtrar per dni: DniInici = , DniFi = .<br>"
                + "Per Sexe: filtrar per sexe (M o F) o no filtrar (tots).<br>"
                + "Per Símptomes: Seleccionar en llista símptomes (botó +, seleccionar, afegir i acceptar), "
                + "filtra els pacients que presentin els símptomes seleccionats.<br>"
                + "Per Malalties: Seleccionar en llista malalties (botó +, seleccionar, afegir i acceptar), "
                + "filtra els pacients que presentin les malalties seleccionades.<br>"
                + "Escollir pacients manualment: Seleccionar en llista pacients (botó +, seleccionar, afegir i acceptar), "
                + "filtra els pacients escollits.<br>"
                + "<u>Parametres:</u><br>"
                + "Q: enter positiu. Indica el número mínim de seqüències on ha d'aparèixer un patró per a "
                + "ser considerat vàlid i presentar-se als resultats.<br>"
                + "Error: real positiu entre 0 i 100 (%). Indica l'error màxim d'error que pot presentar un "
                + "patró respecte al seu match a una seqüència per a ser considerat vàlid.<br>"
                + "Longitud Mínima: enter positiu. Indica la longitud mínima en nucleòtids que ha de presentar "
                + "un patró per a ser considerat vàlid.<br>"
                + "Longitud Màxima: enter positiu. Indica la longitud màxima en nucleòtids que ha de presentar "
                + "un patró per a ser considerat vàlid.<br>"
                + "Paralel: Seleccionar si es vol executar l'estudi en paralel, aprofitant els nuclis de procés "
                + "de l'ordinador al màxim.<br>"
                + "Ins&Del: Seleccionar si es volen tenir en compte Insertions i Deletions a més a més de "
                + "Mutations en la cerca de patrons.<br><br>"
                + "<b>3.3. Veure Resultats:</b><br>"
                + "<u>3.3.1. Veure resultat gràfic:</u><br>"
                + "<u>3.3.2. Veure resultat estadístic:</u><br>"
                + "<u>3.3.3. Conclusions automàtiques</u><br></html>";
        
        jTextHelp.setText(help);
        
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextHelp = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTextHelp.setContentType("text/html");
        jTextHelp.setEditable(false);
        jScrollPane1.setViewportView(jTextHelp);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextHelp;
    // End of variables declaration//GEN-END:variables
}
