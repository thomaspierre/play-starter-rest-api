package microDon.clients.models;

import microDon.Bank;

import java.util.List;

/**
 * Author: tpierre
 */
public class GetBanksResponse {

	private List<Bank> resources;

	public List<Bank> getResources() {
		return resources;
	}

	public void setResources(List<Bank> resources) {
		this.resources = resources;
	}
}
