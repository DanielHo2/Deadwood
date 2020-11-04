import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

import java.util.ArrayList;

public class ParseXML{

   public static Set[] parseBoardFile() throws ParserConfigurationException {
		// first open the xml file
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = null;
		
		File f = new File("xml/board.xml");

    	try{
        	doc = db.parse(f);
      	} catch (Exception ex){
        	System.out.println("XML parse failure");
        	ex.printStackTrace();
		}

      // then parse it to get Set information
      ArrayList<Set> result = new ArrayList<>();
      ArrayList<ArrayList<String>> neighborNames = new ArrayList<>();

      Element root = doc.getDocumentElement();

      NodeList sets = root.getElementsByTagName("set");

      for(int i = 0; i < sets.getLength(); i++) {
         Node set = sets.item(i);

         SetParseResult r = parseSetNode(set);

         result.add(r.set);
         neighborNames.add(r.neighborNames);
      }

      Node office = root.getElementsByTagName("office").item(0);
      SetParseResult o = parseSetNode(office, "office");

      result.add(o.set);
      neighborNames.add(o.neighborNames);

      Node trailer = root.getElementsByTagName("trailer").item(0);
      SetParseResult t = parseSetNode(trailer, "trailer");

      result.add(t.set);
      neighborNames.add(t.neighborNames);

      Set[] toReturn = new Set[result.size()];
      for(int i = 0; i < result.size(); i++) {
         toReturn[i] = result.get(i);
      }

      // at this point, toReturn has all of the sets that will be on
      // the board, but none of these sets have their neighbors set - 
      // however, we have the names of their neighbors in neighborNames.
      // to finish off, we can iterate through neighborNames and all
      // 12 sets to add these neighbors.
      for(int i = 0; i < neighborNames.size(); i++) {
         ArrayList<String> neighborList = neighborNames.get(i);

         // take each neighbor name in the current list,
         // and compare it to every set's name - if there's
         // a match, add it to the current neighbors array
         for(String neighbor : neighborList) {
            for(Set s : toReturn) {
               if(s.getName().equals(neighbor)) toReturn[i].addNeighbor(s);
            }
         }
      }

      // toReturn is now a complete array of Sets, with all neighbors filled in.
      return toReturn;
   }

   public static Scene[] parseCardsFile() throws ParserConfigurationException {
      // first open the xml file
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = null;
		
		File f = new File("xml/cards.xml");

    	try{
        	doc = db.parse(f);
      	} catch (Exception ex){
        	System.out.println("XML parse failure");
        	ex.printStackTrace();
      }

      // then parse it for all of the Scenes contained within
      // by the rules of Deadwood, we know there are exactly
      // 40 scene cards, so we don't need to do all the
      // ArrayList shenanigans like above.
      Scene[] result = new Scene[40];

      Element root = doc.getDocumentElement();

      NodeList cards = root.getElementsByTagName("card");

      for(int i = 0; i < cards.getLength(); i++) {
         ArrayList<Role> parts = parsePartsNode(cards.item(i));
         // (convert parts to an array)
         Role[] partsArray = new Role[parts.size()];

         for(int j = 0; j < parts.size(); j++) {
            partsArray[j] = parts.get(j);
         }

         String name = cards.item(i).getAttributes().getNamedItem("name").getNodeValue();
         String img  = cards.item(i).getAttributes().getNamedItem("img").getNodeValue();
         int budget =  Integer.parseInt( cards.item(i).getAttributes().getNamedItem("budget").getNodeValue() );

         String description = "";

         NodeList children = cards.item(i).getChildNodes();

         for(int j = 0; j < children.getLength(); j++) {
            if(children.item(j).getNodeName().equals("scene")) {
               description = children.item(j).getTextContent().trim();
            }
         }

         result[i] = new Scene(budget, name, img, description, partsArray);
      }

      return result;
   }

   // parseSetNode needs to return the Set (without its
   // neighbors set yet) as well as the *names* of all of
   // its neighbors, so that parseBoardFile above can
   // fill in the actual neighbors
   private static class SetParseResult {
      public ArrayList<String> neighborNames;
      public Set set;

      public SetParseResult(){}
   }
   
   private static SetParseResult parseSetNode(Node set)
   {
      return parseSetNode(set, "");
   }

   private static SetParseResult parseSetNode(Node set, String nameOverride)
   {
      String setName = null;

      if(nameOverride.equals("")) {
         setName = set.getAttributes().getNamedItem("name").getNodeValue();
      } else {
         setName = nameOverride;
      }

      Area setArea = null;
      ArrayList<String> setNeighborNames = null; // start with names, and then fill in the actual sets later
      ArrayList<Role> setParts = null;
      ArrayList<Area> setShotAreas = null;

      NodeList children = set.getChildNodes();

      for(int j = 0; j < children.getLength(); j++) {
         Node sub = children.item(j);

         if(sub.getNodeName().equals("neighbors")) {
            setNeighborNames = parseNeighborsNode(sub);
         } else if(sub.getNodeName().equals("area")) {
            setArea = parseAreaNode(sub);
         } else if(sub.getNodeName().equals("parts")) {
            setParts = parsePartsNode(sub);
         } else if(sub.getNodeName().equals("takes")) {
            setShotAreas = parseTakesNode(sub);
         }
      }

      Area[] setShotAreasArray = null;

      if(setShotAreas != null) {
         setShotAreasArray = new Area[setShotAreas.size()];
         for(int i = 0; i < setShotAreasArray.length; i++) {
            setShotAreasArray[i] = setShotAreas.get(i);
         }
      }

      Role[] setPartsArray = null;
      
      if(setParts != null) {
         setPartsArray = new Role[setParts.size()];
         for(int i = 0; i < setPartsArray.length; i++) {
            setPartsArray[i] = setParts.get(i);
         }
      }

      SetParseResult result = new SetParseResult();
      result.set = new Set(setName, setArea, setShotAreasArray, setPartsArray);
      result.neighborNames = setNeighborNames;

      return result;
   }

   private static Area parseAreaNode(Node area)
   {
      int x = Integer.parseInt( area.getAttributes().getNamedItem("x").getNodeValue() );
      int y = Integer.parseInt( area.getAttributes().getNamedItem("y").getNodeValue() );
      int h = Integer.parseInt( area.getAttributes().getNamedItem("h").getNodeValue() );
      int w = Integer.parseInt( area.getAttributes().getNamedItem("w").getNodeValue() );

      return new Area(x, y, h, w);
   }

   private static ArrayList<String> parseNeighborsNode(Node n)
   {
      NodeList neighbors = n.getChildNodes();

      ArrayList<String> result = new ArrayList<>();

      for(int i = 0; i < neighbors.getLength(); i++) {
         if(neighbors.item(i).getNodeName().equals("neighbor")) {
            result.add(neighbors.item(i).getAttributes().getNamedItem("name").getNodeValue());
         }
      }

      return result;
   }

   private static ArrayList<Role> parsePartsNode(Node n) 
   {
      NodeList parts = n.getChildNodes();
      ArrayList<Role> result = new ArrayList<>();

      for(int i = 0; i < parts.getLength(); i++) {
         Node part = parts.item(i);

         if(part.getNodeName().equals("part")) {

            String name = part.getAttributes().getNamedItem("name").getNodeValue();
            int level = Integer.parseInt( part.getAttributes().getNamedItem("level").getNodeValue() );
            Area a = new Area(0,0,0,0);
            String line = "This text should not appear";

            NodeList children = part.getChildNodes();


            for(int j = 0; j < children.getLength(); j++) {
               Node child = children.item(j);
               
               if(child.getNodeName().equals("area")) {
                  a = parseAreaNode(child);
               } else if(child.getNodeName().equals("line")) {
                  line = child.getTextContent();
               }
            }

            result.add( new Role(level, name, line, a, true) );
         }
      }

      return result;
   }

   // the visual positions of each shot counter - the size of the 
   // resulting ArrayList doubles as the number of shots for the
   // set being processed.
   private static ArrayList<Area> parseTakesNode(Node takes) 
   {
      NodeList takeList = takes.getChildNodes();
      ArrayList<Area> result = new ArrayList<>();

      for(int i = 0; i < takeList.getLength(); i++) {
         Node take = takeList.item(i);

         NodeList area = take.getChildNodes();

         for(int j = 0; j < area.getLength(); j++) {
            Node a = area.item(j);

            if(a.getNodeName().equals("area")) {
               result.add( parseAreaNode(a) );
            }
         }
      }

      return result;
   }

}//class