package com.awcsoftware.app.client;

public enum ClientMessageConstants {
	
	BlankClientName("Client name can't be blank"),
	BlankStartDate("Start date can't be blank"),
	BlankEndDate("End can't be balnk"),
	BlankStatus("Status can't be blank"),
	DataNotFound("Data not found"),
	ClientAddedSuccessFully("Client added successfully"),
	EndDateCantBeforeStartDate("End date can't before the start date");
	
	private final String label;

	public String getLabel() {
		return label;
	}

	private ClientMessageConstants(String label) {
		this.label = label;
	}

}
