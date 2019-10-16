package cmsc420.meeshquest.part1;
import java.util.*;


import org.w3c.dom.Document;
import org.w3c.dom.Element;



public class Commands {
	protected Document results; 
	protected Element res; 
	protected ArrayList<ArrayList<String>> Cities = new ArrayList<>(); 
	
	public void setResults(Document Results) {
		this.results = Results;
		res = results.createElement("results");
		results.appendChild(res);
	}
	
	private String helpAttributes(Element cmd, String Aname, Element param) {
		String v = cmd.getAttribute(Aname);
		Element attr = results.createElement(Aname);
		attr.setAttribute("value", v);
		param.appendChild(attr);
		return v;
	}
	
	public void pcreatecity(Element elem){
		//getting command node
		Element cmd = results.createElement("command");
		cmd.setAttribute("name", elem.getNodeName());
		Element param = results.createElement("parameters");
		
		ArrayList<String> arr = new ArrayList<String>() ;
		String name = helpAttributes(elem,"name",param); arr.add(name);
		
		//int x = Integer.parseInt(setAttributes(cmd,"x",param));
		//int y = Integer.parseInt(setAttributes(cmd,"y",param));
		//int radius = Integer.parseInt(setAttributes(cmd,"radius",param));
		//String color = setAttributes(cmd,"color",param);
		
		String x = helpAttributes(elem,"x",param); arr.add(x);
	    String y = helpAttributes(elem,"y",param); arr.add(y);
        String radius = helpAttributes(elem,"radius",param); arr.add(radius);
        String color = helpAttributes(elem,"color",param); arr.add(color);
				
        Cities.add(arr);
		Element output = results.createElement("output");
		
		Element s = results.createElement("success");
		s.appendChild(cmd);
		s.appendChild(param);
		s.appendChild(output);
		res.appendChild(s);
	}
	
	private void putCity(Element where, Object input) {
		Element city = results.createElement("city");
		ArrayList<String> others = (ArrayList<String>) input;
		city.setAttribute("name", others.get(0));
		city.setAttribute("x", others.get(1));
		city.setAttribute("y", others.get(2));
		city.setAttribute("radius",others.get(3));
		city.setAttribute("color", others.get(4));
		where.appendChild(city);
	}
	
	public void plistcities(Element elem) {
		Element cmd = results.createElement("command");
		cmd.setAttribute("name", elem.getNodeName());
		Element param = results.createElement("parameters");
		
		String key = helpAttributes(elem,"sortBy",param);
		
		Element output = results.createElement("output");
		Element CL = results.createElement("cityList");
		if(! Cities.isEmpty()) {
			
			if (key.equals("name")) {
				
				Collections.sort(Cities,new Comparator() {
					public int compare(Object o1, Object o2) {
						String x1 = ((ArrayList<String>) o1).get(0).toLowerCase();
						String x2 = ((ArrayList<String>) o2).get(0).toLowerCase();
						return x1.compareTo(x2);
					}
				});
				
				for (Object i: Cities) {
					putCity(CL, i);
					
				}
			}
			else if (key.equals("coordinate")) {
				
				Collections.sort(Cities, new Comparator() {
					public int compare(Object o1, Object o2) {
						ArrayList<String> l1 = (ArrayList<String>) o1;
						ArrayList<String> l2 = (ArrayList<String>) o2;
						if(Integer.parseInt(l1.get(1)) == Integer.parseInt(l2.get(1))) {
							return Integer.parseInt(l1.get(2)) - Integer.parseInt(l2.get(2));
						}
						else{
							return Integer.parseInt(l1.get(1)) - Integer.parseInt(l2.get(1));
						}
					}

				});
				
				for (Object i: Cities) {
					putCity(CL,i);
					
				}
			}
			
			output.appendChild(CL);
			
		    Element success = results.createElement("success");
			success.appendChild(cmd);
			success.appendChild(param);
			success.appendChild(output);
			res.appendChild(success);
		}
	}
}
