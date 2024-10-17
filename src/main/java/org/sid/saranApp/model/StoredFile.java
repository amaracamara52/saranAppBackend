package org.sid.saranApp.model;

import java.util.Objects;

import javax.persistence.Entity;



@Entity
public class StoredFile extends AbstractDomainClass {
	
	private String name;
	private String type;
	private byte[] bytes;
	
	public StoredFile() {
	        super();
	    }

	    public StoredFile(String name, String type, byte[] bytes) {
	        this.name = name;
	        this.type = type;
	        this.bytes = bytes;
	    }

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public byte[] getBytes() {
			return bytes;
		}

		public void setBytes(byte[] bytes) {
			this.bytes = bytes;
		}
	    
	    
		   @Override
		    public boolean equals(Object o) {
		        if (this == o)
		            return true;
		        if (!(o instanceof StoredFile))
		            return false;
		        StoredFile file = (StoredFile) o;
		        return Objects.equals(this.uuid, file.uuid) && Objects.equals(this.name, file.name);
		    }

		    @Override
		    public int hashCode() {
		        return Objects.hash(this.uuid, this.name);
		    }


}
