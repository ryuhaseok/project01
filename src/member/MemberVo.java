package member;


public class MemberVo {
	private int memberId;
	private String id;
	private String pw;
	private String name;
	private String hp;
	private String birth;
	private int departmentId;
	private String hireDate;
	private int managerId;
	private String position;
	
	
	public MemberVo() {
		super();
	}

	public MemberVo(String id, String pw) {
		super();
		this.id = id;
		this.pw = pw;
	}

	public MemberVo(String id, String pw, String name, String hp, String birth, String hireDate) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.hp = hp;
		this.birth = birth;
		this.hireDate = hireDate;
	}

	public MemberVo(int memberId, String id, String pw, String name, String hp, String birth, int departmentId,
			String hireDate, int managerId, String position) {
		super();
		this.memberId = memberId;
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.hp = hp;
		this.birth = birth;
		this.departmentId = departmentId;
		this.hireDate = hireDate;
		this.managerId = managerId;
		this.position = position;
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

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getHireDate() {
		return hireDate;
	}

	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "MemberVo [memberId=" + memberId + ", id=" + id + ", pw=" + pw + ", name=" + name + ", hp=" + hp
				+ ", birth=" + birth + ", departmentId=" + departmentId + ", hireDate=" + hireDate + ", managerId="
				+ managerId + ", position=" + position + "]";
	}
	
}