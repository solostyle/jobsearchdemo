package com.example.androidcourse_assignment3;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XMLNode {
	
	private Node node = null;
	
	private XMLNode(Node node) {
		this.node = node; 
	}
	
	public XMLNode(InputStream in) {
		if(in != null) {
			try {
				// create the document that will allow us to parse the XML input stream
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();

				
				node = builder.parse(in);

				node.normalize();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}  
	}

	public XMLNode(String xml) {
		this(new ByteArrayInputStream(xml.getBytes()));
	}
	
	public Node getNode() {
		return node;
	}
	
	public XMLNode nodeForXpath(String path) {
		XMLNode resultNode = null; 
		
		List<XMLNode> nodes = this.nodesForXpath(path);
		
		if(nodes.size() > 0) {
			resultNode = nodes.get(0);
		}
		
		return resultNode; 
	}
	
	public List<XMLNode> nodesForXpath(String path) {
		
		List<XMLNode> nodes = new ArrayList<XMLNode>();
		List<String> command = XMLNode.parseXpath(path); 
		
		if(this.getNode() != null) {
			
			for(int i = 0 ; i < command.size(); i++) {
				
				String com = command.get(i);
				
				if(com == "/") {
					
					if(i + 1 < command.size()){
						i++; 
						com = command.get(i); 
						
						if(nodes.isEmpty()) {
							nodes = this.getFirstLevelNodeList(com, this.getNode());
						} else {
							
							List<XMLNode> tmpNodes = new ArrayList<XMLNode>(); 
							for(XMLNode node : nodes) {
								
								List<XMLNode>tmpNodes2 = this.getFirstLevelNodeList(com, node.getNode());
								if(tmpNodes2.size() > 0) {
									tmpNodes.addAll(tmpNodes2);
								}
							}
							
							nodes = tmpNodes; 
						}
					} else {
						throw new RuntimeException("INCORRECT XPATH");
					}
				} else if(com == "//") {
					
					if(i + 1 < command.size()){
						i++; 
						com = command.get(i); 
						
						if(nodes.isEmpty()) {
							nodes = this.getAnyLevelNodeList(com, this.getNode());
						} else {
							
							List<XMLNode> tmpNodes = new ArrayList<XMLNode>(); 
							for(XMLNode node : nodes) {
								
								List<XMLNode> tmpNodes2 = this.getAnyLevelNodeList(com, node.getNode()); 
								if(tmpNodes2.size() > 0){
									tmpNodes.addAll(tmpNodes2);
								}
							}
							
							nodes = tmpNodes; 
						}
					} else {
						throw new RuntimeException("INCORRECT XPATH");
					}
				} else if(com == "@") {
					throw new RuntimeException("NOT SUPPORTED FOR GET NODELIST");
				} else if(com == ".") {
					if(nodes.isEmpty()) {
						nodes.add(new XMLNode(this.getNode()));
					}
				} else {
					if(nodes.isEmpty()) {
						nodes = this.getAnyLevelNodeList(com, this.getNode());
					} else {
						
						List<XMLNode> tmpNodes = new ArrayList<XMLNode>(); 
						for(XMLNode node : nodes) {
							
							List<XMLNode> tmpNodes2 = this.getAnyLevelNodeList(com, node.getNode()); 
							if(tmpNodes2.size() > 0) {
								tmpNodes.addAll(tmpNodes2);
							}
						}
						
						nodes = tmpNodes; 
					}
				}
				
			}	
		} 
		return nodes; 
	}
	
	
	private List<XMLNode> getAnyLevelNodeList(String elementName, Node node) {
		
		List<XMLNode> nodes = new ArrayList<XMLNode>(); 
		
		NodeList tmpNodeList = node.getChildNodes(); 
		
		for(int i = 0; i < tmpNodeList.getLength(); i++) {		
			Node tmpNode = tmpNodeList.item(i);
			
			if(elementName.equals(tmpNode.getNodeName())) {
				nodes.add(new XMLNode(tmpNode));
			}

			if(tmpNode.hasChildNodes()) {
				nodes.addAll(this.getAnyLevelNodeList(elementName, tmpNode));
			}
		}
		return nodes; 
	}
	
	private List<XMLNode> getFirstLevelNodeList(String elementName, Node node) {
		
		List<XMLNode> nodes = new ArrayList<XMLNode>(); 
		
		NodeList tmpNodeList = node.getChildNodes();
		for(int i = 0 ; i < tmpNodeList.getLength(); i++) {
			if(elementName.equals(tmpNodeList.item(i).getNodeName())) {
				nodes.add(new XMLNode(tmpNodeList.item(i)));
			}
		}
		
		return nodes; 
	}
	
	/**
	 * simple parsing function that only support nodename, /, //, ., @
	 * @param path
	 * @return
	 */
	static List<String> parseXpath(String path) {
		List<String> result = new ArrayList<String>(); 
	
		String tmp = ""; 
		for(int i = 0 ; i < path.length(); i++) {
			char c = path.charAt(i); 
			
			if(c == '.') {
				
				if(tmp.length() > 0){
					result.add(tmp);
					tmp = "";
				}
				result.add(".");
			} else if(c == '/') {
				// peak the next one 
				if(i + 1 < path.length() && path.charAt(i + 1) == '/') {
					
					if(tmp.length() > 0) {
						result.add(tmp); 
						tmp = ""; 
					}
					result.add("//");
				} else {
					
					if(tmp.length() > 0) {
						result.add(tmp);
						tmp = ""; 
					}
					result.add("/");
				}
			} else if(c =='@'){
				if(tmp.length() > 0) {
					result.add(tmp);
					tmp = ""; 
				}
				result.add("@");
			}else {
				tmp += c; 
			}	
		}
		
		if(tmp.length() > 0) {
			result.add(tmp);
			tmp = "";
		}
		
		
		return result; 
	}
	
	public String getAttributeStringValue(String attName) {
		Node tmp = this.getNode().getAttributes().getNamedItem(attName);
		return tmp.getNodeValue(); 
	}
	
	public String getElementStringValue() {
		
		if(this.getNode() != null && this.getNode().getFirstChild() != null) {
			return this.getNode().getFirstChild().getNodeValue();
		} else {
			return null;
		}
	}
	
	public Boolean getElementBooleanValue() {
		try {
			return Boolean.parseBoolean(this.getElementStringValue());
		} catch (Exception e) {
			return Boolean.FALSE; 
		}
	}
	
	public Date getElementDateValue() {
		return DateConvert.FromShortString(this.getElementStringValue());
	}
	
	public Date getElementDateValue(String format) {
		return DateConvert.FromString(this.getElementStringValue(), format);
	}
	
	public Double getElementDoubleValue() {
		Double result = null; 
		try{
			result = Double.parseDouble(this.getElementStringValue());
		} catch(NumberFormatException e){
			e.printStackTrace(); 
		}
		return result; 
				
	}
	
	public Integer getElementIntegerValue() {
		Integer result = null; 
		try{
			result = Integer.parseInt(this.getElementStringValue());
		} catch(NumberFormatException e){
			e.printStackTrace(); 
		}
		return result; 
	}
	
	public String getElementName() {
		if(this.getNode() != null) {
			return this.getNode().getNodeName();
		} else {
			return null; 
		}
	}
	
	
}
