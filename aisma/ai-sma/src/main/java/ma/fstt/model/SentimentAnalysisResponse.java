package ma.fstt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SentimentAnalysisResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String tag;
	private List<DataSet> datasets;
	private List<String> labels;
	
	
	
	public SentimentAnalysisResponse() {
		labels = new ArrayList<String>();
		
		datasets = new ArrayList<DataSet>();
		datasets.add(new DataSet());
		labels.add("positive");
		labels.add("negative");
		
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<DataSet> getDatasets() {
		return datasets;
	}

	public void setDatasets(List<DataSet> datasets) {
		this.datasets = datasets;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public class DataSet implements Serializable{
		private static final long serialVersionUID = 1L;
		
		private List<Integer> data;
		private List<String> backgroundColor;
		
		public DataSet() {
			data = new ArrayList<Integer>();
			backgroundColor = new ArrayList<String>();
			data.add(0);
			data.add(0);
			backgroundColor.add("rgba(153, 255, 102, 1)");
			backgroundColor.add("rgba(255, 102, 102, 1)");
		}

		public List<Integer> getData() {
			return data;
		}

		public void setData(List<Integer> data) {
			this.data = data;
		}

		public List<String> getBackgroundColor() {
			return backgroundColor;
		}

		public void setBackgroundColor(List<String> backgroundColor) {
			this.backgroundColor = backgroundColor;
		}
	}
}


