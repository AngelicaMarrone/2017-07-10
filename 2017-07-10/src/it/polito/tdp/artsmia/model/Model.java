package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {

	//inserire tipo di dao

		private ArtsmiaDAO dao;

		

		//scelta valore mappa

		private Map<Integer,ArtObject> idMap;

		

		//scelta tipo valori lista

		private List<ArtObject> vertex;

		

		//scelta tra uno dei due edges

		private List<Adiacenza> edges;

		

		//scelta tipo vertici e tipo archi

		private Graph<ArtObject, DefaultWeightedEdge> graph;
		
		private ArtObject source;
		private ArtObject target;

		public Model() {

			

			//inserire tipo dao

			dao  = new ArtsmiaDAO();

			//inserire tipo values

			idMap = new HashMap<Integer,ArtObject>();

		}

		

		public void creaGrafo() {

			

			//scelta tipo vertici e archi

			graph = new SimpleWeightedGraph<ArtObject,DefaultWeightedEdge>(DefaultWeightedEdge.class);

			

			//scelta tipo valori lista

			vertex = new ArrayList<ArtObject>(dao.listObjects(idMap));

			Graphs.addAllVertices(graph,vertex);

			

			edges = new ArrayList<Adiacenza>(dao.getEdges());

			

			for(Adiacenza a : edges) {

			 source = idMap.get(a.getId1());

			 target = idMap.get(a.getId2());

				double peso = a.getPeso();
				
				DefaultWeightedEdge e= graph.getEdge(source, target);
				if (e==null)
				{try{Graphs.addEdge(graph,source,target,peso);}catch(NullPointerException n) {}

				//System.out.println("AGGIUNTO ARCO TRA: "+source.toString()+" e "+target.toString());}

				

			}}

			

			System.out.println("#vertici: " + graph.vertexSet().size());

			System.out.println("#archi: " + graph.edgeSet().size());

			

		}



		public boolean esiste(Integer cod) {
			
			ArtObject a= idMap.get(cod);
			
			if (a==null)
			{ return false;}
			
			return this.graph.containsVertex(idMap.get(cod));
		}



		public int componenteConnessa(Integer cod) {
			
			ArtObject a= idMap.get(cod);
			
			Set<ArtObject> visitati = new HashSet<>(); 
			DepthFirstIterator<ArtObject, DefaultWeightedEdge> dfv = new DepthFirstIterator<>(this.graph, a); 
			while (dfv.hasNext()) 
			{visitati.add(dfv.next());}
			
			return visitati.size();

			
		}
		}
	

