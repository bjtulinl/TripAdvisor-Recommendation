package tut1.consumer;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Paragraph;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Compound;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;


public class Comments_split extends JCasAnnotator_ImplBase{

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		// TODO Auto-generated method stub
		String str = aJCas.getDocumentText();
		String[] comments = str.split("\n");
		Compound c = new Compound(aJCas);
		c.setBegin(str.indexOf(comments[2].split(" ")[1].trim()));
		c.setEnd(str.indexOf(comments[2].split(" ")[1].trim())+comments[2].split(" ")[1].trim().length());
		c.addToIndexes();
		for(int i=4; i<comments.length;i++) {
			if(comments[i].length()!=0) {
				Paragraph s = new Paragraph(aJCas);
				s.setBegin(str.indexOf(comments[i]));
				s.setEnd(str.indexOf(comments[i])+comments[i].length()-1);
				s.addToIndexes();
			}
		}
	}

}
