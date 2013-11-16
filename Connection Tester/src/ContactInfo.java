
public class ContactInfo {

	private String organization;
	private String contactPerson;
	private String contactPhone;

	public ContactInfo() {
		this.organization = new String("");
		this.contactPerson = new String("");
		this.contactPhone = new String("");
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
}
