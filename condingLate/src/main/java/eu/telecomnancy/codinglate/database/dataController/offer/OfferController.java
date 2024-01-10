package eu.telecomnancy.codinglate.database.dataController.offer;

import eu.telecomnancy.codinglate.database.DbConnection;
import eu.telecomnancy.codinglate.database.dataController.user.PersonController;
import eu.telecomnancy.codinglate.database.dataObject.enums.PriceType;
import eu.telecomnancy.codinglate.database.dataObject.enums.ProductCategory;
import eu.telecomnancy.codinglate.database.dataObject.enums.ProductCondition;
import eu.telecomnancy.codinglate.database.dataObject.offer.Offer;
import eu.telecomnancy.codinglate.database.dataObject.offer.Product;
import eu.telecomnancy.codinglate.database.dataObject.offer.Service;
import eu.telecomnancy.codinglate.database.dataObject.user.Person;
import eu.telecomnancy.codinglate.database.dataObject.user.User;
import eu.telecomnancy.codinglate.geolocation.Coordinates;
import eu.telecomnancy.codinglate.geolocation.Geolocation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class OfferController {

    public void insert(Service service) {
        if (service.getUser().getId() == -1) {
            System.out.println("L'utilisateur doit être inséré dans la base de données avant l'insertion de l'offre.");
            return;
        }
        try (Connection conn = DbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO offer (user, title, description, price, priceType, startingDate, endingDate, category, brand, model, condition, year, service) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, service.getUser().getId());
            pstmt.setString(2, service.getTitle());
            pstmt.setString(3, service.getDescription());
            pstmt.setDouble(4, service.getPrice());
            pstmt.setInt(5, service.getPriceType().ordinal());
            pstmt.setObject(6, service.getStartingDate());
            pstmt.setObject(7, service.getEndingDate());
            pstmt.setNull(8, Types.INTEGER); // category (null for Service)
            pstmt.setNull(9, Types.VARCHAR); // brand (null for Service)
            pstmt.setNull(10, Types.VARCHAR); // model (null for Service)
            pstmt.setNull(11, Types.INTEGER); // condition (null for Service)
            pstmt.setNull(12, Types.INTEGER); // year (null for Service)
            pstmt.setInt(13, 1); // offerType (1 for Service)

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1); // Récupérer l'ID généré
                        service.setId(generatedId);
                        System.out.println("Service inserted successfully with ID " + generatedId);
                    } else {
                        System.out.println("Aucun ID généré trouvé.");
                    }
                }
            } else {
                System.out.println("L'insertion a échoué.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de manière appropriée, par exemple, en lançant une exception personnalisée
        }
    }

    public void insert(Product product) {
        try (Connection conn = DbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO offer (user, title, description, price, priceType, startingDate, endingDate, category, brand, model, condition, year, service) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, product.getUser().getId());
            pstmt.setString(2, product.getTitle());
            pstmt.setString(3, product.getDescription());
            pstmt.setDouble(4, product.getPrice());
            pstmt.setInt(5, product.getPriceType().ordinal());

            if (product.getStartingDate() != null) {
                pstmt.setObject(6, product.getStartingDate());
            } else {
                pstmt.setNull(6, Types.DATE);
            }

            if (product.getEndingDate() != null) {
                pstmt.setObject(7, product.getEndingDate());
            } else {
                pstmt.setNull(7, Types.DATE);
            }

            pstmt.setInt(8, product.getCategory().ordinal()); // category (not null for Product)
            pstmt.setString(9, product.getBrand()); // brand (not null for String)
            pstmt.setString(10, product.getModel()); // model (not null String)

            if (product.getCondition() == null) {
                pstmt.setNull(11, Types.INTEGER);
            } else {
                pstmt.setObject(11, Types.INTEGER);
            }

            pstmt.setInt(12, product.getYear()); // year (not null int)
            pstmt.setInt(13, 0); // offerType (0 for Product)

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1); // Récupérer l'ID généré
                        product.setId(generatedId);
                        System.out.println("Product inserted successfully with ID " + generatedId);
                    } else {
                        System.out.println("Aucun ID généré trouvé.");
                    }
                }
            } else {
                System.out.println("L'insertion a échoué.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de manière appropriée, par exemple, en lançant une exception personnalisée
        }
    }

    public void update(Service service) {
        if (service.getId() == -1) {
            System.out.println("Service non trouvé dans la base de données.");
            return;
        }

        try (Connection conn = DbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(
                     "UPDATE offer SET title = ?, description = ?, price = ?, priceType = ?, " +
                             "startingDate = ?, endingDate = ? WHERE id = ?")) {

            pstmt.setString(1, service.getTitle());
            pstmt.setString(2, service.getDescription());
            pstmt.setDouble(3, service.getPrice());
            pstmt.setInt(4, service.getPriceType().ordinal());
            pstmt.setObject(5, service.getStartingDate(), Types.DATE);
            pstmt.setObject(6, service.getEndingDate(), Types.DATE);
            pstmt.setInt(7, service.getId());

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Service updated successfully.");
            } else {
                System.out.println("Aucune mise à jour effectuée.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de manière appropriée, par exemple, en lançant une exception personnalisée
        }
    }

    public void update(Product product) {
        if (product.getId() == -1) {
            System.out.println("Le produit doit être inséré dans la base de données avant la mise à jour.");
            return;
        }

        try (Connection conn = DbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(
                     "UPDATE offer SET user=?, title=?, description=?, price=?, priceType=?, startingDate=?, endingDate=?, category=?, brand=?, model=?, condition=?, year=? WHERE id=?")) {

            pstmt.setInt(1, product.getUser().getId());
            pstmt.setString(2, product.getTitle());
            pstmt.setString(3, product.getDescription());
            pstmt.setDouble(4, product.getPrice());
            pstmt.setInt(5, product.getPriceType().ordinal());

            if (product.getStartingDate() != null) {
                pstmt.setObject(6, product.getStartingDate());
            } else {
                pstmt.setNull(6, Types.DATE);
            }

            if (product.getEndingDate() != null) {
                pstmt.setObject(7, product.getEndingDate());
            } else {
                pstmt.setNull(7, Types.DATE);
            }

            pstmt.setInt(8, product.getCategory().ordinal()); // Category for Product

            if (product.getBrand() != null) {
                pstmt.setString(9, product.getBrand());
            } else {
                pstmt.setNull(9, Types.VARCHAR);
            }

            if (product.getModel() != null) {
                pstmt.setString(10, product.getModel());
            } else {
                pstmt.setNull(10, Types.VARCHAR);
            }

            if (product.getCondition() != null) {
                pstmt.setInt(11, product.getCondition().ordinal());
            } else {
                pstmt.setNull(11, Types.INTEGER);
            }

            pstmt.setInt(12, product.getYear());
            pstmt.setInt(13, product.getId());

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Product updated successfully with ID " + product.getId());
            } else {
                System.out.println("La mise à jour a échoué. Aucun produit trouvé avec cet ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de manière appropriée, par exemple, en lançant une exception personnalisée
        }
    }

    public void delete(Offer offer) {
        delete(offer.getId());
    }

    public void delete(int offerId) {
        try (Connection conn = DbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM offer WHERE id = ?")) {

            pstmt.setInt(1, offerId);

            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Offre avec l'ID " + offerId + " supprimée avec succès.");
            } else {
                System.out.println("Aucune offre avec l'ID " + offerId + " trouvée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de manière appropriée, par exemple, en lançant une exception personnalisée
        }
    }

    private Product createProduct(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int userId = resultSet.getInt("user");
        String title = resultSet.getString("title");
        String description = resultSet.getString("description");
        double price = resultSet.getDouble("price");
        PriceType priceType = PriceType.values()[resultSet.getInt("priceType")];

        LocalDate startingDate = null;
        if (resultSet.getObject("startingDate") == null) {
            System.out.println("Starting date is null");

        } else {
            startingDate = resultSet.getObject("startingDate", LocalDate.class);
        }

        LocalDate endingDate = null;
        if (resultSet.getObject("endingDate") == null) {
            System.out.println("Ending date is null");
        } else {
            endingDate = resultSet.getObject("endingDate", LocalDate.class);
        }

        ProductCategory category = ProductCategory.values()[resultSet.getInt("category")];
        String brand = resultSet.getString("brand");
        String model = resultSet.getString("model");

        ProductCondition condition = null;
        if (resultSet.getObject("condition") == null) {
            System.out.println("Condition is null");
        } else {
            System.out.println(resultSet.getInt("condition"));
            condition = ProductCondition.values()[resultSet.getInt("condition")];
        }

        int conditionOrdinal = resultSet.getInt("condition");
        int year = resultSet.getInt("year");

        PersonController userController = new PersonController();
        Person person = userController.getPersonById(userId);

        // Check if the person is a user
        if (!(person instanceof User user)) {
            System.out.println("L'utilisateur avec l'ID " + userId + " n'existe pas.");
            return null;
        }


        return new Product(id, user, title, description, price, priceType, startingDate, endingDate, category, brand, model, year, condition, new ArrayList<>());
    }

    private Service createService(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int userId = resultSet.getInt("user");
        String title = resultSet.getString("title");
        String description = resultSet.getString("description");
        double price = resultSet.getDouble("price");
        PriceType priceType = PriceType.values()[resultSet.getInt("priceType")];

        LocalDate startingDate = null;
        if (resultSet.getObject("startingDate") == null) {
            System.out.println("Starting date is null");

        } else {
            startingDate = resultSet.getObject("startingDate", LocalDate.class);
        }

        LocalDate endingDate = null;
        if (resultSet.getObject("endingDate") == null) {
            System.out.println("Ending date is null");
        } else {
            endingDate = resultSet.getObject("endingDate", LocalDate.class);
        }

        PersonController userController = new PersonController();
        Person person = userController.getPersonById(userId);

        // Vérifier si la personne est un utilisateur
        if (!(person instanceof User user)) {
            System.out.println("L'utilisateur avec l'ID " + userId + " n'existe pas.");
            return null;
        }
        // TODO : Ajouter les images
        return new Service(id, user, title, description, price, priceType, startingDate, endingDate, new ArrayList<>());
    }

    public Offer getOfferById(int offerId) {
        Offer offer = null;
        try (Connection conn = DbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM offer WHERE id = ?")) {

            pstmt.setInt(1, offerId);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt("user");
                    int categoryOrdinal = resultSet.getInt("category");

                    if (categoryOrdinal == 0) {
                        // Il s'agit d'un service
                        offer = createService(resultSet);
                    } else {
                        // Il s'agit d'un produit
                        offer = createProduct(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de manière appropriée, par exemple, en lançant une exception personnalisée
        }
        return offer;
    }

    public ArrayList<Product> getProductsByName(String name) {
        ArrayList<Product> products = new ArrayList<>();
        try (Connection conn = DbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM offer WHERE LOWER(title) LIKE ? AND service = 0")) {

            pstmt.setString(1, "%" + name.toLowerCase() + "%");

            try (ResultSet resultSet = pstmt.executeQuery()) {
                int i = 0;
                while (resultSet.next()) {
                    Product product = createProduct(resultSet);
                    if (product != null) {
                        products.add(product);
                    }
                    i++;
                }
                System.out.println(i + " produits trouvés.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de manière appropriée, par exemple, en lançant une exception personnalisée
        }
        return products;
    }

    public ArrayList<Offer> getOfferByParameters(Boolean service, ProductCategory category, String brand, String model, ProductCondition condition, int year) {
        ArrayList<Offer> offers = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM offer WHERE 1=1");

        if (service != null) {
            query.append(" AND service = ").append(service ? 0 : 1);
        }

        if (category != null) {
            query.append(" AND category = ").append(category.ordinal());
        }

        if (brand != null && !brand.isEmpty()) {
            query.append(" AND brand = '").append(brand).append("'");
        }

        if (model != null && !model.isEmpty()) {
            query.append(" AND model = '").append(model).append("'");
        }

        if (condition != null) {
            query.append(" AND condition = ").append(condition.ordinal());
        }

        if (year > 1) {
            query.append(" AND year = ").append(year);
        }

        System.out.println(query.toString());
        try (Connection conn = DbConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query.toString())) {

            try (ResultSet resultSet = pstmt.executeQuery()) {
                int i = 0;
                while (resultSet.next()) {
                    // Déterminez le type d'offre en fonction de la catégorie
                    int categoryOrdinal = resultSet.getInt("category");
                    Offer offer;
                    if (categoryOrdinal == 0) {
                        // Service
                        offer = createService(resultSet);
                    } else {
                        // Product
                        offer = createProduct(resultSet);
                    }

                    if (offer != null) {
                        offers.add(offer);
                    }

                    i++;
                }
                System.out.println(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de manière appropriée, par exemple, en lançant une exception personnalisée
        }

        return offers;
    }


    public ArrayList<Offer> checkDistance(ArrayList<Offer> offers, Person person, int distance) {
        if (person == null || person.getAddress() == null) {
            System.out.println("L'utilisateur doit avoir une adresse pour vérifier la distance.");
            return new ArrayList<>();
        }

        if (distance <= 0) {
            System.out.println("La distance doit être supérieure à 0.");
            return offers;
        }


        ArrayList<Offer> offersInRange = new ArrayList<>();

        for (Offer offer : offers) {
            if (offer.getUser() == null || offer.getUser().getAddress() == null) {
                System.out.println("L'offre doit avoir une adresse pour vérifier la distance.");
                continue;
            } else {

                if (Geolocation.getDistance(person, offer) <= distance) {
                    offersInRange.add(offer);
                }
            }

        }
        return offersInRange;
    }
}
