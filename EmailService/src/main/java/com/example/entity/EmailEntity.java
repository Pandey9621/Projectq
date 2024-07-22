package com.example.entity;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class EmailEntity {
	 // Class data members
    private String recipient;
    private String msgBody;
    private String subject;
//    private String attachment;
//	public String getAttachment() {
//		return attachment;
//	}
//
//	public void setAttachment(String attachment) {
//		this.attachment = attachment;
//	}

	public String getRecipient() {
		return recipient;
	}
	
	public EmailEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmailEntity(String recipient, String msgBody, String subject) {
		super();
		this.recipient = recipient;
		this.msgBody = msgBody;
		this.subject = subject;
//		this.attachment = attachment;
	}

	public String getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	

	
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
    


}
