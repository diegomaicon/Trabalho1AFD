package lib;

import modelo.Afd;
import modelo.State;
import modelo.Transition;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Diego on 19/04/2017.
 */
public class XMLmanipulation {

    public XMLmanipulation() {
    }

    /**
     *
     *   Abre Arquivo .jff do JFlap e carrega em um objeto
     *   da Classe AFD
     *
     *  @return m Afd
     *
     *  @param caminho String
     *
     * */
    public Afd load(String caminho) {
        Transition trans = null;
        State state = null;
        Afd afd = new Afd();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(caminho);
            if (doc != null) {
                NodeList listState = doc.getElementsByTagName("state");
                ArrayList<State> e = new ArrayList<State>();
                ArrayList<State> eF = new ArrayList<State>();

                for (int i = 0; i < listState.getLength(); i++) {
                    Node noState = listState.item(i);
                    if (noState.getNodeType() == Node.ELEMENT_NODE) {
                        Element elementoState = (Element) noState;
                        state = new State();

                        String id = elementoState.getAttribute("id");
                        state.setId(Integer.parseInt(id));

                        NodeList listFilhoState = elementoState.getChildNodes();

                        for (int j = 0; j < listFilhoState.getLength(); j++) {
                            Node noFilho = listFilhoState.item(j);
                            if (noFilho.getNodeType() == Node.ELEMENT_NODE) {
                                Element elementoFilho = (Element) noFilho;

                                switch (elementoFilho.getTagName()) {

                                    case "initial":
                                        state.seteInicial(true);
                                        afd.seteInicial(state);
                                        break;
                                    case "final":
                                        state.seteFinal(true);
                                        eF.add(state);
                                        break;

                                    case "x":
                                        state.setX(elementoFilho.getTextContent());
                                        break;

                                    case "y":
                                        state.setY(elementoFilho.getTextContent());
                                        break;

                                }
                            }
                        }
                    }
                    e.add(state);
                }
                afd.seteFinal(eF);
                afd.setEstado(e);

                NodeList listTrans = doc.getElementsByTagName("transition");
                ArrayList<Transition> t = new ArrayList<Transition>();
                ArrayList<Character> alafabeto = new ArrayList<Character>();
                for (int i = 0; i < listTrans.getLength(); i++) {
                    Node noTrans = listTrans.item(i);
                    if (noTrans.getNodeType() == Node.ELEMENT_NODE) {
                        Element elementoTrans = (Element) noTrans;
                        trans = new Transition();

                        NodeList listFilhoTrans = elementoTrans.getChildNodes();

                        for (int j = 0; j < listFilhoTrans.getLength(); j++) {
                            Node noFilhoT = listFilhoTrans.item(j);
                            if (noFilhoT.getNodeType() == Node.ELEMENT_NODE) {
                                Element elementoFilhoT = (Element) noFilhoT;

                                switch (elementoFilhoT.getTagName()) {
                                    case "from":
                                        for (State aux : e) {
                                            if (aux.getId() == Integer.parseInt(elementoFilhoT.getTextContent())) {
                                                trans.setFrom(aux);
                                            }
                                        }
                                        break;
                                    case "to":
                                        for (State aux : e) {
                                            if (aux.getId() == Integer.parseInt(elementoFilhoT.getTextContent())) {
                                                trans.setTo(aux);
                                            }
                                        }
                                        break;
                                    case "read":
                                        if (!alafabeto.contains(elementoFilhoT.getTextContent().charAt(0))) {
                                            alafabeto.add(elementoFilhoT.getTextContent().charAt(0));
                                        }
                                        trans.setRead(elementoFilhoT.getTextContent().charAt(0));
                                        break;
                                }
                            }
                        }
                    }
                    t.add(trans);
                }
                afd.setAlfabeto(alafabeto);
                afd.setFuncTransicao(t);

            }

        } catch(ParserConfigurationException e){
                e.printStackTrace();
        } catch(SAXException e){
                e.printStackTrace();
        } catch(IOException e){
               return afd;
        }

        return afd;
    }

    /**
     * Recebe um objeto do Tipo Afd e salva em um arquivo XML .jff
     * para ser aberto no JFlap
     *
     * @param m
     * @param caminho
     */

    public void salve(Afd m, String caminho) {

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document documentXML = documentBuilder.newDocument();

            Element root = documentXML.createElement("structure");
            documentXML.appendChild(root);

            Element type = documentXML.createElement("type");
            type.appendChild(documentXML.createTextNode("fa"));
            root.appendChild(type);

            Element automaton = documentXML.createElement("automaton");
            root.appendChild(automaton);



            for (State e : m.getEstado()) {
                Element state = documentXML.createElement("state");
                Attr id = documentXML.createAttribute("id");
                id.setValue(String.valueOf(e.getId()));
                Attr name = documentXML.createAttribute("name");
                name.setValue("q" + String.valueOf(e.getId()));

                state.setAttributeNode(id);
                state.setAttributeNode(name);

                Element x = documentXML.createElement("x");
                x.appendChild(documentXML.createTextNode(e.getX()));
                state.appendChild(x);

                Element y = documentXML.createElement("y");
                y.appendChild(documentXML.createTextNode(e.getY()));
                state.appendChild(y);

                if (e.iseInicial()) {
                    Element ini = documentXML.createElement("initial");
                    state.appendChild(ini);
                }

                if (e.iseFinal()) {
                    Element fin = documentXML.createElement("final");
                    state.appendChild(fin);
                }

                automaton.appendChild(state);
            }


            for (Transition t : m.getFuncTransicao()) {
                Element transition = documentXML.createElement("transition");

                Element from = documentXML.createElement("from");
                from.appendChild(documentXML.createTextNode(String.valueOf(t.getFrom().getId())));
                transition.appendChild(from);

                Element to = documentXML.createElement("to");
                to.appendChild(documentXML.createTextNode(String.valueOf(t.getTo().getId())));
                transition.appendChild(to);

                Element read = documentXML.createElement("read");
                read.appendChild(documentXML.createTextNode(t.getRead().toString()));
                transition.appendChild(read);

                automaton.appendChild(transition);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(documentXML);

            StreamResult documentoFinal = new StreamResult(new File(caminho));
            transformer.transform(domSource,documentoFinal);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

}
