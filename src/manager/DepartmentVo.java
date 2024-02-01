package manager;


public class DepartmentVo {
	private int memberId;
	private String id;
	private String pw;
	private String name;
	private String hp;
	private String birth;
	private String hireDate;
	private String position;
	private int departmentId;
	private String departmentName;
	private int managerId;
	private String positionName;
	private int count;

	public DepartmentVo(int departmentId, String departmentName, String name, int count) {
		super();
		this.name = name;
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.count = count;
	}

	public int getCountPosition() {
		return count;
	}

	public void setCountPosition(int countPosition) {
		this.count = count;
	}

	public DepartmentVo() {
		super();
	}

	public DepartmentVo(String departmentName) {
		super();
		this.departmentName = departmentName;
	}

	public DepartmentVo(int memberId, String id, String pw, String name, String hp, String birth, String hireDate,
			String position, int departmentId, String departmentName, int managerId, String positionName) {
		super();
		this.memberId = memberId;
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.hp = hp;
		this.birth = birth;
		this.hireDate = hireDate;
		this.position = position;
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.managerId = managerId;
		this.positionName = positionName;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHp() {
		return hp;
	}

	public void setHp(String hp) {
		this.hp = hp;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getHireDate() {
		return hireDate;
	}

	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

}