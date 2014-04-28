package br.com.lol.observador;

public interface Observer {
	
	public void update(boolean status, int contador);
	public void setChanges();

}
