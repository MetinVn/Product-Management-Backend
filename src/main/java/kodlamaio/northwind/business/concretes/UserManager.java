package kodlamaio.northwind.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kodlamaio.northwind.business.abstracts.UserService;
import kodlamaio.northwind.core.dataAccess.UserDao;
import kodlamaio.northwind.core.entities.User;
import kodlamaio.northwind.core.utilities.results.DataResult;
import kodlamaio.northwind.core.utilities.results.Result;
import kodlamaio.northwind.core.utilities.results.SuccessDataResult;
import kodlamaio.northwind.core.utilities.results.SuccessResult;

@Service
public class UserManager implements UserService {

    private UserDao userDao;

    @Autowired
    public UserManager(UserDao userDao) {
	super();
	this.userDao = userDao;
    }

    @Override
    public Result add(User user) {
	this.userDao.save(user);
	return new SuccessResult("Kullanici eklendi");
    }

    @Override
    public DataResult<User> findByEmail(String email) {
	return new SuccessDataResult<User>(this.userDao.findByEmail(email), "Kullanici bulundu");
    }

    @Override
    public User login(String email, String password) {
	User user = this.userDao.findByEmail(email);
	if (user != null && user.getPassword().equals(password)) {
	    return user;
	}
	return null; // Invalid credentials
    }
}
