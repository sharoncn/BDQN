package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Models;

public class TrafficInfo {
	private double startUpload, startDownload, startAll, endUpload,
			endDownload, endAll;
	private long startPackages, endPackages;
	private String day;

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public double getStartUpload() {
		return startUpload;
	}

	public void setStartUpload(double startUpload) {
		this.startUpload = startUpload;
	}

	public double getStartDownload() {
		return startDownload;
	}

	public void setStartDownload(double startDownload) {
		this.startDownload = startDownload;
	}

	public double getStartAll() {
		return startAll;
	}

	public void setStartAll(double startAll) {
		this.startAll = startAll;
	}

	public double getEndUpload() {
		return endUpload;
	}

	public void setEndUpload(double endUpload) {
		this.endUpload = endUpload;
	}

	public double getEndDownload() {
		return endDownload;
	}

	public void setEndDownload(double endDownload) {
		this.endDownload = endDownload;
	}

	public double getEndAll() {
		return endAll;
	}

	public void setEndAll(double endAll) {
		this.endAll = endAll;
	}

	public long getStartPackages() {
		return startPackages;
	}

	public void setStartPackages(long startPackages) {
		this.startPackages = startPackages;
	}

	public long getEndPackages() {
		return endPackages;
	}

	public void setEndPackages(long endPackages) {
		this.endPackages = endPackages;
	}

}
