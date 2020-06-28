package com.crookedcoder.behaviorapp.feed;

import java.util.List;
import java.util.Optional;

import com.crookedcoder.behaviorapp.dao.DAO;

public class FeedDAO implements DAO<Feed> {

    private Feed feed = new Feed();
         
    public FeedDAO() {
        
    }

    
    @Override
    public Optional<Feed> get(long id) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<Feed> getAll() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void save(Feed t) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void update(Feed t, String[] params) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void delete(Feed t) {
        // TODO Auto-generated method stub
        
    }
}
// @Override
// public Optional<User> get(long id) {
//     return Optional.ofNullable(users.get((int) id));
// }

// @Override
// public List<User> getAll() {
//     return users;
// }

// @Override
// public void save(User user) {
//     users.add(user);
// }

// @Override
// public void update(User user, String[] params) {
//     user.setName(Objects.requireNonNull(params[0], "Name cannot be null"));
//     user.setEmail(Objects.requireNonNull(params[1], "Email cannot be null"));

//     users.add(user);
// }

// @Override
// public void delete(User user) {
//     users.remove(user);
// }

/**
     * Prints the date, UID, behavior key and unit completed of users from spreadsheet:
     * https://docs.google.com/spreadsheets/d/1SrCbIbgXSCtM2jz2x1qC7dmiVjVQVf1M_fWpuBIs_hc/edit
     */
    // public static void getFeed() throws IOException, GeneralSecurityException {
    //     // Build a new authorized API client service.
    //     final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    //     final String range = "feed!A3:D";
    //     Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
    //             .setApplicationName(APPLICATION_NAME)
    //             .build();
    //     ValueRange response = service.spreadsheets().values()
    //             .get(SPREADSHEET_ID, range)
    //             .execute();
    //     List<List<Object>> values = response.getValues();
    //     // For validation that response is received and appropriately processed.
    //     if (values == null || values.isEmpty()) {
    //         System.out.println("No data found.");
    //     } else {
    //         System.out.println("Date, UID, Behavior, Units Completed");
    //         for (List row : values) {
    //             // Print columns A through D, which correspond to the indices below.
    //             System.out.printf("%s, %s, %s, %s,\n", row.get(0), row.get(1), row.get(2), row.get(3));
    //         }
    //     }
    // }