package metric;

public class Pasta {

	private Integer mes;
	private Integer loc = 0;
	private Integer classes = 0;
	private Integer method = 0;
	private Integer classGod = 0;
	private Integer methodGod = 0;
	private Integer forWhile = 0;
	private Integer ifs = 0;

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Integer getLoc() {
		return loc;
	}

	public void setLoc(Integer loc) {
		this.loc = loc;
	}

	public Integer getClasses() {
		return classes;
	}

	public void setClasses(Integer classes) {
		this.classes = classes;
	}

	public Integer getMethod() {
		return method;
	}

	public void setMethod(Integer method) {
		this.method = method;
	}

	public Integer getClassGod() {
		return classGod;
	}

	public void setClassGod(Integer classGod) {
		this.classGod = classGod;
	}

	public Integer getMethodGod() {
		return methodGod;
	}

	public void setMethodGod(Integer methodGod) {
		this.methodGod = methodGod;
	}

	public Integer getForWhile() {
		return forWhile;
	}

	public void setForWhile(Integer forWhile) {
		this.forWhile = forWhile;
	}

	public Integer getIfs() {
		return ifs;
	}

	public void setIfs(Integer ifs) {
		this.ifs = ifs;
	}

	@Override
	public String toString() {
		return mes + "," + loc + "," + classes + "," + method + "," + classGod + "," + methodGod + "," + forWhile + "," + ifs;
	}

}
