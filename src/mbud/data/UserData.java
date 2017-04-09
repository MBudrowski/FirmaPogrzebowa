package mbud.data;

public class UserData {

	private String imie, nazwisko, login;
	private int id = 0;
	private boolean isWorker = false;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setImie(String imie) {
		this.imie = imie;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}
	
	public int getId() {
		return id;
	}
	
	public String getImie() {
		return imie;
	}
	
	public String getLogin() {
		return login;
	}
	
	public String getNazwisko() {
		return nazwisko;
	}
	
	public boolean isLoggedIn() {
		return id != 0;
	}
	
	public boolean hasWorkerPrivs() {
		return isWorker;
	}
	
	public void setWorkerPrivs(boolean isWorker) {
		this.isWorker = isWorker;
	}
}
