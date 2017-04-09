package mbud.data;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class LockManager {
	
	private static class Lock {
		private HttpSession session;
		private Date date;
		
		public Lock(HttpSession session) {
			this.session = session;
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.MINUTE, 5);
			this.date = c.getTime();
		}
		
		public HttpSession getSession() {
			return session;
		}
		
		public Date getDate() {
			return date;
		}
	}
	
	private static Map<Integer, Lock> lockMap = new HashMap<>();

	public static boolean isFuneralLocked(int funeralId) {
		Date now = new Date();
		for (Map.Entry<Integer, Lock> i : lockMap.entrySet()) {
			if (i.getKey() == funeralId) {
				if (i.getValue().getDate().before(now)) {
					lockMap.remove(i.getKey());
					return false;
				}
				else {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isFuneralLocked(int funeralId, HttpSession session) {
		Date now = new Date();
		for (Map.Entry<Integer, Lock> i : lockMap.entrySet()) {
			if (i.getKey() == funeralId) {
				if (i.getValue().getDate().before(now)) {
					lockMap.remove(i.getKey());
					return false;
				}
				else if (i.getValue().getSession().getId().equals(session.getId())) {
					return false;
				}
				else {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isStillLockedBy(int funeralId, HttpSession session) {
		Date now = new Date();
		for (Map.Entry<Integer, Lock> i : lockMap.entrySet()) {
			if (i.getKey() == funeralId) {
				if (i.getValue().getDate().before(now)) {
					lockMap.remove(i.getKey());
					return false;
				}
				else if (i.getValue().getSession().getId().equals(session.getId())) {
					return true;
				}
				return false;
			}
		}
		return false;
	}
	
	public static void addLock(int funeralId, HttpSession session) {
		Lock lock = new Lock(session);
		lockMap.put(funeralId, lock);
	}
	
	public static void removeLock(int funeralId) {
		lockMap.remove(funeralId);
	}
	
}
