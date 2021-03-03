package com.shubham.daoImpl;

import com.shubham.dao.UserRepository;
import com.shubham.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserDaoImpl {

    @Autowired
    UserRepository userRepository;


    HashMap<String, Integer> balanceHashMap = new HashMap<>();
    HashMap<String, Integer> deductHashMap = null;
    int balanceTracker = 0;
    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public int getBalancePoints() {
        int sum = 0;
        List<User> usersList = userRepository.findAll();

        for (User user : usersList) {
            sum += user.getPoints();
        }

        return sum;
    }


    public HashMap<String, Integer> balance() {
        return balanceHashMap;
    }


    public LinkedHashMap<String, Integer> updatePoints(int pointsToUpdate) {
        LinkedHashMap<String, Integer> pointsSub = new LinkedHashMap<>();
        List<User> usersList = userRepository.findAll();
        if (usersList.size() > 1) {
            usersList.sort(new CompareDate());
        }

        if (pointsToUpdate <= 0 || pointsToUpdate > balanceTracker)
            return new LinkedHashMap<>();
        try {
            int points;
            int pointsToSub;
            for (User user : usersList) {
                if (user.getPoints() < 0)
                    continue;
                if (deductHashMap.get(user.getPayer()) == null) {
                    points = 0;
                } else {
                    points = deductHashMap.get(user.getPayer());
                }

                points += user.getPoints();
                if (points > 0) {
                    pointsToSub = Math.min(pointsToUpdate, points);
                    pointsToUpdate -= pointsToSub;
                    pointsSub.putIfAbsent(user.getPayer(), 0);
                    pointsSub.put(user.getPayer(), pointsSub.get(user.getPayer()) - pointsToSub);
                    points = 0;
                }
                deductHashMap.put(user.getPayer(), points);
                if (pointsToUpdate <= 0)
                    return pointsSub;
            }
        } finally {

            pointsSub.forEach((key, value) -> {
                User addUser = new User();
                addUser.setPayer(key);
                addUser.setPoints(value);
                addUser.setTimestamp(formatDate.format(new Date()));
                userRepository.save(addUser);
                usersList.add(addUser);
            });
            updateBalance();
        }
        return pointsSub;
    }


    public void updateBalance() {
        List<User> usersList = userRepository.findAll();
        if (usersList.size() > 1) {
            usersList.sort(new CompareDate());
        }
        balanceTracker = 0;
        deductHashMap = new HashMap<>();
        balanceHashMap = new HashMap<>();
        for (User user : usersList) {
            if (user.getPoints() < 0) {
                deductHashMap.putIfAbsent(user.getPayer(), 0);
                deductHashMap.put(user.getPayer(), deductHashMap.get(user.getPayer()) + user.getPoints());
            }
            balanceHashMap.putIfAbsent(user.getPayer(), 0);
            balanceHashMap.put(user.getPayer(), balanceHashMap.get(user.getPayer()) + user.getPoints());
            balanceTracker += user.getPoints();
        }
    }

}

class CompareDate implements Comparator<User> {
    @Override
    public int compare(User u1, User u2) {
        String x = u1.getTimestamp();
        String y = u2.getTimestamp();
        int result = x.compareTo(y);
        return result;
    }
}



