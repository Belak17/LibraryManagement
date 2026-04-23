package application;
	
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import dao.* ;
import bookpackage.Book;
import bookpackage.Emprunt;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import systemusers.Admin;
import systemusers.Librarian;
import systemusers.User;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame ;
import javafx.animation.KeyValue;
import javafx.util.Duration ; 

public class Tutorial extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			showLogin(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void showLogin(Stage primaryStage) {

	    // ====== FORMULAIRE ======
	    VBox vbox = new VBox(15);
	    vbox.setAlignment(Pos.CENTER);
	    vbox.setPrefWidth(360);
	    BorderPane.setMargin(vbox, new Insets(0, 40, 0, 0));

	    TextField username = new TextField();
	    username.setPromptText("Username");
	    username.setMaxWidth(300);

	    PasswordField password = new PasswordField();
	    password.setPromptText("Password");
	    password.setMaxWidth(300);
	    
	    // Image du login 
	    Image image ; 
        image = new Image(getClass().getResourceAsStream("/resources/loginimg.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(540);
        imageView.setFitHeight(540);
        imageView.setPreserveRatio(true);
	    //ChoiceBox<String> choiceBox = new ChoiceBox<>();
	    //choiceBox.getItems().addAll("Admin", "Librarian", "User");
	    //choiceBox.setPrefWidth(300);

	    Button login = new Button("Login");
	    login.setPrefWidth(150);
	    
	    login.setStyle("""
	        -fx-background-color: #3b82f6;
	        -fx-text-fill: white;
	        -fx-font-weight: bold;
	        -fx-background-radius: 10;
	    """);
	    Button register = new Button("Register ?");
	    register.setPrefWidth(160);
	    register.setStyle("""
	        -fx-background-color: #3b82f6;
	        -fx-text-fill: white;
	        -fx-font-weight: bold;
	        -fx-background-radius: 10;
	    """);
	    register.setOnAction(e -> showRegister(primaryStage));

	    login.setOnAction(e -> {
	    		if (!username.getText().isEmpty()&&!password.getText().isEmpty())
	    		{
	    			try {
						if(UserDAO.searchUser(username.getText())!=null)
						{
							User user = UserDAO.searchUser(username.getText());
							if (user.getPassword().equals(password.getText()))
							{
							
								if (user.getRole().equals("ADMIN"))
								{
									showAdminDashboard(primaryStage,user);
								}
								else if (user.getRole().equals("LIBRARIAN"))
									
								{
									showLibrarianDashboard(primaryStage,user);
								}
								else if(user.getRole().equals("USER"))
								{
									/*UserDAO.insertKaleb();*/
									showUserDashboard(primaryStage,user);
									
								}
							 
									
							}
							else 
							{
								new Alert(Alert.AlertType.INFORMATION, "Wrong password").show();
								username.clear();
					    	    password.clear();
							}
						}
						else 
						{
							new Alert(Alert.AlertType.INFORMATION, "username not found  , Register if not ").show();
							 username.clear();
				    	     password.clear();
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	    		}
	    		else 
	    		{
	    			System.out.println("New");
	    			new Alert(Alert.AlertType.INFORMATION, "Password and username are required ").show();

	    	        username.clear();
	    	        password.clear();
	    		}
	    }
	    	
			
			);

	    Text logo = new Text("LIBRARY MANAGEMENT");
	    logo.setFill(Color.BLACK);
	    logo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	    vbox.getChildren().addAll(logo,username, password, login, register);
	    Rectangle rightPanel = new Rectangle(360,270);
	    rightPanel.setArcHeight(80);
	    rightPanel.setArcWidth(80);
	    rightPanel.setFill(Color.WHITE);
	    rightPanel.setStroke(Color.BLACK);
	   
	    Rectangle leftPanel = new Rectangle(480, 540);
	    leftPanel.setFill(Color.web("#0F172A"));
	    
	   
	    BorderPane root = new BorderPane();
	    root.setPrefSize(960, 540);
	    root.setLeft(new StackPane(leftPanel,imageView));
	    //root.setRight(rightPanel);
	    StackPane pane = new StackPane(rightPanel,vbox);
	    
	    root.setRight(pane);
	    BorderPane.setMargin(pane, new Insets(0,30,0,0));
	    Scene scene = new Scene(root);
	    primaryStage.setScene(scene);
	    primaryStage.show();
	    primaryStage.setResizable(false);
	}

	public void showRegister(Stage primaryStage) {

	    
	    VBox vbox = new VBox(15);
	    vbox.setAlignment(Pos.CENTER);
	    vbox.setPrefWidth(360);
	    BorderPane.setMargin(vbox, new Insets(0, 40, 0, 0));

	    TextField username = new TextField();
	    username.setPromptText("Username");
	    username.setMaxWidth(300);

	    PasswordField password = new PasswordField();
	    password.setPromptText("Password");
	    password.setMaxWidth(300);
	    
	 // Image du login 
	    Image image ; 
        image = new Image(getClass().getResourceAsStream("/resources/loginimg.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(540);
        imageView.setFitHeight(540);
        imageView.setPreserveRatio(true);

        
	    Button register = new Button("Register");
	    register.setPrefWidth(160);
	    register.setStyle("""
	        -fx-background-color: #3b82f6;
	        -fx-text-fill: white;
	        -fx-font-weight: bold;
	        -fx-background-radius: 10;
	    """);
	    register.setPrefWidth(150);
	    register.setOnAction(e->{
	    	if (!username.getText().isEmpty())
	    	{
	    		try {
					if (UserDAO.searchUser(username.getText())==null)
					{
						if (!password.getText().isEmpty())
						{
							UserDAO.insertUser(new User(username.getText(),password.getText()));
							new Alert(Alert.AlertType.INFORMATION, "Congratulations you've been registered").show();
						}
						else 
						{
							
							new Alert(Alert.AlertType.INFORMATION, "Enter something as password").show();
						}
					}
					else 
					{
						new Alert(Alert.AlertType.INFORMATION, "User found , Please login").show();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    	}
	    });
	    Button returnLogin = new Button("Return");
	    returnLogin.setPrefWidth(160);
	    returnLogin.setStyle("""
	        -fx-background-color: #3b82f6;
	        -fx-text-fill: white;
	        -fx-font-weight: bold;
	        -fx-background-radius: 10;
	    """);
	    returnLogin.setOnAction(e -> showLogin(primaryStage));
	    Text logo = new Text("LIBRARY MANAGEMENT");
	    logo.setFill(Color.BLACK);
	    logo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	    vbox.getChildren().addAll(logo,username, password, register, returnLogin);

	    
	    Rectangle leftPanel = new Rectangle(480, 540);
	    leftPanel.setFill(Color.BLUE);
	    
	    Rectangle rightPanel = new Rectangle(360,270);
	    rightPanel.setArcHeight(80);
	    rightPanel.setArcWidth(80);
	    rightPanel.setFill(Color.WHITE);
	    rightPanel.setStroke(Color.BLACK);
	    
	    BorderPane root = new BorderPane();
	    root.setPrefSize(960, 540);
	    root.setLeft(new StackPane(leftPanel,imageView));
	    root.setRight(vbox);
	    StackPane pane = new StackPane(rightPanel,vbox);
	    
	    root.setRight(pane);
	    BorderPane.setMargin(pane, new Insets(0,30,0,0));
	    Scene scene = new Scene(root);
	    primaryStage.setScene(scene);
	    primaryStage.show();
	    primaryStage.setResizable(false);
	}

		private void showAdminDashboard(Stage primaryStage, User user) {
	
	    BorderPane root = new BorderPane();
	
	    /* ===================== SIDEBAR ===================== */
	    VBox sidebar = new VBox(18);
	    sidebar.setPadding(new Insets(25));
	    sidebar.setPrefWidth(200);
	    sidebar.setStyle("""
	        -fx-background-color: #1e1e2f;
	    """);
	
	    Text logo = new Text("ADMIN");
	    logo.setFill(Color.WHITE);
	    logo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	    Button btnSeeUser = createSidebarButton("See User",e->{
	    	try {
				seeUser(primaryStage,UserDAO.ListUser(),sidebar);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    });
	    
	    Button btnDelLibrarian= createSidebarButton("Delete Librarian",e->{showDeleteLibrarian(primaryStage, sidebar);});
	    Button btnAddLibrarian = createSidebarButton("Add Librarian", e -> {showAddLibrarian(primaryStage, sidebar);});
	    Button btnModBook   = createSidebarButton("Modify Book", e -> {
			
				showModifyBook(primaryStage,sidebar);
			
		});
	    Button btnAddBook   = createSidebarButton("Add Book", e -> showAddBook(primaryStage,sidebar));
	    Button btnLibrarian = createSidebarButton("Librarians", e -> {
			try {
				seeLibrarian(primaryStage,UserDAO.ListLibrarian(), sidebar);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	    Button btnDeleteBook    = createSidebarButton("Delete Book", e -> showDelBook(primaryStage, sidebar));
	    Button returndashboard = createSidebarButton("Dashboard", e-> showAdminDashboard(primaryStage,user));
	    sidebar.getChildren().addAll(
	            logo,
	            new Separator(),
	            btnAddLibrarian,
	            btnDelLibrarian,
	            btnLibrarian,
	            btnModBook,
	            btnAddBook,
	          
	            btnDeleteBook,
	            btnSeeUser,
	            
	            returndashboard 
	    );
	
	    /* ===================== HEADER ===================== */
	    HBox header = new HBox();
	    header.setPadding(new Insets(15, 25, 15, 25));
	    header.setAlignment(Pos.CENTER_RIGHT);
	    header.setStyle("""
	        -fx-background-color: white;
	        -fx-border-color: #e0e0e0;
	        -fx-border-width: 0 0 1 0;
	    """);
	
	    Button logout = new Button("Logout");
	    logout.setOnAction(e -> showLogin(primaryStage));
	    header.getChildren().add(logout);
	
	    /* ===================== CONTENT ===================== */
	    VBox content = new VBox(25);
	    content.setPadding(new Insets(30));
	    content.setStyle("-fx-background-color: #f4f6fb;");
	
	    Text welcome = new Text("Welcome, Admin ");
	    welcome.setFont(Font.font("Arial", FontWeight.BOLD, 24));
	
	    Text subtitle = new Text("Manage your library system");
	    subtitle.setFill(Color.GRAY);
	    createAdminCard("Profil","See your profil", e->{
	    	showOneProfil(primaryStage,user,sidebar);
	    });
	    HBox cards = new HBox(20);
	    cards.getChildren().addAll(
	    		createAdminCard("Profil","See your profil", e->{
	    	    	showOneProfil(primaryStage,user,sidebar);
	    	    }),
	            createAdminCard("Library", "View all books", e -> {
					try {
						showBook(primaryStage,BookDAO.getAllBooks(),sidebar);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}),
	            createAdminCard("Add Book", "Create new entry", e -> showAddBook(primaryStage,sidebar)),
	            createAdminCard("Librarians", "Manage staff", e -> {try {
					seeLibrarian(primaryStage,UserDAO.ListLibrarian(), sidebar);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}}));
	    
	
	    content.getChildren().addAll(welcome, subtitle, cards);
	
	    /* ===================== ASSEMBLY ===================== */
	    root.setLeft(sidebar);
	    root.setTop(header);
	    root.setCenter(content);
	
	    Scene scene = new Scene(root, 1000, 600);
	    primaryStage.setScene(scene);
	    primaryStage.setTitle("Admin Dashboard");
	    primaryStage.show();
	}
		private void showLibrarianDashboard(Stage primaryStage,User user) {
		
		    BorderPane root = new BorderPane();
		
		    /* ===================== SIDEBAR ===================== */
		    VBox sidebar = new VBox(18);
		    sidebar.setPadding(new Insets(25));
		    sidebar.setPrefWidth(200);
		    sidebar.setStyle("""
		        -fx-background-color: #1e1e2f;
		    """);
		
		    Text logo = new Text("LIBRARIAN");
		    logo.setFill(Color.WHITE);
		    logo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		    Button btnSeeUser = createSidebarButton("See User",e->{
		    	try {
					seeUser(primaryStage,UserDAO.ListUser(),sidebar);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    });
		   
		    Button returnDashboard = createSidebarButton("Dashboard", e->showLibrarianDashboard(primaryStage,user));
		    Button btnAddBookside= createSidebarButton("Add Book",e->{showAddBook(primaryStage,sidebar);});
		    Button btnModBook = createSidebarButton("Modify Book", e -> {showModifyBook(primaryStage, sidebar);});
		    Button btnLibrary   = createSidebarButton("Library", e -> {
				try {
					showBook(primaryStage,BookDAO.getAllBooks(),sidebar);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		    Button btnAddBook   = createSidebarButton("Add Book", e -> showAddBook(primaryStage,sidebar));
		    
		    Button btnDeleteBook    = createSidebarButton("DeleteBook", e -> showDelBook(primaryStage, sidebar));
		    
		    sidebar.getChildren().addAll(
		            logo,
		            new Separator(),
		            btnAddBookside,
		            btnModBook,
		            btnLibrary,
		            btnAddBook,
		            
		            btnDeleteBook,
		            btnSeeUser,
		            returnDashboard 
		    );
		
		    //HEADER 
		    HBox header = new HBox();
		    header.setPadding(new Insets(15, 25, 15, 25));
		    header.setAlignment(Pos.CENTER_RIGHT);
		    header.setStyle("""
		        -fx-background-color: white;
		        -fx-border-color: #e0e0e0;
		        -fx-border-width: 0 0 1 0;
		    """);
		
		    Button logout = new Button("Logout");
		    logout.setOnAction(e -> showLogin(primaryStage));
		    header.getChildren().add(logout);
		
		    /* ===================== CONTENT ===================== */
		    VBox content = new VBox(25);
		    content.setPadding(new Insets(30));
		    content.setStyle("-fx-background-color: #f4f6fb;");
		
		    Text welcome = new Text("Welcome, Librarian ");
		    welcome.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		
		    Text subtitle = new Text("Manage your library system");
		    subtitle.setFill(Color.GRAY);
		
		    HBox cards = new HBox(20);
		    cards.getChildren().addAll(
		            createAdminCard("Library", "View all books", e -> {
						try {
							showBook(primaryStage,BookDAO.getAllBooks(),sidebar);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}),
		            createAdminCard("Add Book", "Create new entry", e -> showAddBook(primaryStage,sidebar)),
		            createAdminCard("Profil", "See your profil", e -> showOneProfil(primaryStage,user,sidebar)));
		            
		    
		
		    content.getChildren().addAll(welcome, subtitle, cards);
		
		    /* ===================== ASSEMBLY ===================== */
		    root.setLeft(sidebar);
		    root.setTop(header);
		    root.setCenter(content);
		
		    Scene scene = new Scene(root, 1000, 600);
		    primaryStage.setScene(scene);
		    primaryStage.setTitle("Librarian Dashboard");
		    primaryStage.show();
		}
		private void showUserDashboard(Stage primaryStage,User user) {
		
		    BorderPane root = new BorderPane();
		
		    /* ===================== SIDEBAR ===================== */
		    VBox sidebar = new VBox(18);
		    sidebar.setPadding(new Insets(25));
		    sidebar.setPrefWidth(200);
		    sidebar.setStyle("""
		        -fx-background-color: #1e1e2f;
		    """);
		
		    Text logo = new Text("USER");
		    logo.setFill(Color.WHITE);
		    logo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		    Button returnDashboard =  createSidebarButton("Dashboard", e->{
		    	showUserDashboard(primaryStage,user);
		    });
		    Button btnBorrowBook = createSidebarButton("See Borrow" , e->{showBookBorrowed(primaryStage,user,sidebar);});
		    Button btnReturnBook= createSidebarButton("Return Book",e->{showReturnBook(primaryStage,sidebar);});
		    Button btnEmpruntBook = createSidebarButton("Borrow Book", e -> {showBorrowBook(primaryStage,sidebar);});
		    Button btnLibrary   = createSidebarButton("Library", e -> {
				try {
					showBook(primaryStage,BookDAO.getAllBooks(),sidebar);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		    
		    //Button btnLibrarian = createSidebarButton("Librarians", e -> seeLibrarian());
		    
		    
		    sidebar.getChildren().addAll(
		            logo,
		            new Separator(),
		            btnReturnBook,
		            btnEmpruntBook,
		            btnLibrary,
		            btnBorrowBook ,
		            returnDashboard
		    );
		
		    /* ===================== HEADER ===================== */
		    HBox header = new HBox();
		    header.setPadding(new Insets(15, 25, 15, 25));
		    header.setAlignment(Pos.CENTER_RIGHT);
		    header.setStyle("""
		        -fx-background-color: white;
		        -fx-border-color: #e0e0e0;
		        -fx-border-width: 0 0 1 0;
		    """);
		
		    Button logout = new Button("Logout");
		    logout.setOnAction(e -> showLogin(primaryStage));
		    header.getChildren().add(logout);
		
		    /* ===================== CONTENT ===================== */
		    VBox content = new VBox(25);
		    content.setPadding(new Insets(30));
		    content.setStyle("-fx-background-color: #f4f6fb;");
		
		    Text welcome = new Text("Welcome, User");
		    welcome.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		
		    Text subtitle = new Text("Manage your space");
		    subtitle.setFill(Color.GRAY);
		
		    HBox cards = new HBox(20);
		    cards.getChildren().addAll(
		            createAdminCard("Library", "View all books", e -> {
						try {
							showBook(primaryStage,BookDAO.getAllBooks(),sidebar);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}),
		            createAdminCard("Borrow Book", "Create new borrow ", e -> showBorrowBook(primaryStage,sidebar)),
		            createAdminCard("Profil", "See your profil", e -> showOneProfil(primaryStage,user,sidebar)));
		    
		
		    content.getChildren().addAll(welcome, subtitle, cards);
		
		    /* ===================== ASSEMBLY ===================== */
		    root.setLeft(sidebar);
		    root.setTop(header);
		    root.setCenter(content);
		
		    Scene scene = new Scene(root, 1000, 600);
		    primaryStage.setScene(scene);
		    primaryStage.setTitle("User Dashboard");
		    primaryStage.show();
		}
		private void showOneProfil(Stage primaryStage, User user, VBox sidebar) {

		    BorderPane root = new BorderPane();

		    /* ===================== HEADER ===================== */
		    HBox header = new HBox();
		    header.setPadding(new Insets(15, 25, 15, 25));
		    header.setAlignment(Pos.CENTER_RIGHT);
		    header.setStyle("""
		        -fx-background-color: white;
		        -fx-border-color: #e5e7eb;
		        -fx-border-width: 0 0 1 0;
		    """);

		    Button logout = new Button("Logout");
		    logout.setOnAction(e -> showLogin(primaryStage));
		    header.getChildren().add(logout);

		   /* ===================== PROFIL CARD ===================== */
		    Rectangle card = new Rectangle(360, 420);
		    card.setArcWidth(60);
		    card.setArcHeight(60);
		    card.setFill(Color.WHITE);
		    card.setStroke(Color.BLACK);

		    Image image = new Image(getClass().getResourceAsStream("/resources/icon.png"));
		    ImageView imageView = new ImageView(image);
		    imageView.setFitWidth(80);
		    imageView.setFitHeight(80);
		    imageView.setPreserveRatio(true);

		    Label nameLabel = new Label(user.getUsername());
		    Label idLabel = new Label("ID : " + user.getId());
		    Label roleLabel = new Label("Role : " + user.getRole());
		    Label passLabel = new Label("Password : "+user.getPassword());
		    
		    nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #111827;");
		    idLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #111827;");
		    roleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #111827;");
		    passLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #111827;");
		    
		    VBox profileInfo = new VBox(10, imageView, nameLabel, idLabel,passLabel,roleLabel);
		    profileInfo.setAlignment(Pos.CENTER);

		    StackPane profileCard = new StackPane(card, profileInfo);

		    /* ===================== LAYOUT ===================== */
		    root.setTop(header);
		    root.setLeft(sidebar);   // sidebar réelle
		    root.setCenter(profileCard);

		    Scene scene = new Scene(root, 1000, 600);
		    primaryStage.setScene(scene);
		    primaryStage.setTitle("Profil");
		    primaryStage.show();
		}
		private void showBorrowBook(Stage primaryStage,VBox vbox) {

		    // ----------- Labels et Champs ----------
		    Label lblTitle = new Label("Book Title *");
		    lblTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
		    TextField tfTitle = new TextField();
		    tfTitle.setPromptText("Enter book title");
		    tfTitle.setPrefWidth(300);
		    tfTitle.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");

		    Label lblUser = new Label("Username *");
		    lblUser.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
		    TextField tfUser = new TextField();
		    tfUser.setPromptText("Enter username");
		    tfUser.setPrefWidth(300);
		    tfUser.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");

		    Label lblPass = new Label("Password *");
		    lblPass.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
		    PasswordField pfPass = new PasswordField();
		    pfPass.setPromptText("Enter password");
		    pfPass.setPrefWidth(300);
		    pfPass.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");

		    Label lblDateBorrow = new Label("Borrow Date *");
		    lblDateBorrow.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
		    DatePicker dpBorrow = new DatePicker();
		    dpBorrow.setPrefWidth(300);

		    Label lblDateReturn = new Label("Expected Return Date *");
		    lblDateReturn.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
		    DatePicker dpReturn = new DatePicker();
		    dpReturn.setPrefWidth(300);

		    // ----------- Bouton ----------
		    Button btnBorrow = new Button("Borrow Book");
		    btnBorrow.setPrefWidth(160);
		    btnBorrow.setStyle("""
		        -fx-background-color: #3b82f6;
		        -fx-text-fill: white;
		        -fx-font-weight: bold;
		        -fx-background-radius: 10;
		    """);

		    btnBorrow.setOnAction(e -> {
		        if (tfTitle.getText().isEmpty() ||
		            tfUser.getText().isEmpty() ||
		            pfPass.getText().isEmpty() ||
		            dpBorrow.getValue() == null ||
		            dpReturn.getValue() == null) {
		            new Alert(Alert.AlertType.WARNING, "All fields are required").show();
		            return;
		        }

		        String title = tfTitle.getText();
		        String username = tfUser.getText();
		        String password = pfPass.getText();
		        LocalDate dateEmprunt = dpBorrow.getValue();
		        LocalDate dateRetour = dpReturn.getValue();
		        String statut = "EN_COURS";
		        
		        try {
		        	 if (BookEmpruntDAO.searchBookAvailable(BookDAO.searchBook(title).getId()).equals("Disponible"))
		        	 {
		            BookEmpruntDAO.insertEmprunt(new Emprunt(
		                dateEmprunt, dateRetour, statut, BookDAO.searchBook(title),UserDAO.searchUser(username)
		        	 
		            ));
		            new Alert(Alert.AlertType.INFORMATION, "Book borrowed successfully with the id "+BookEmpruntDAO.searchEmpruntByUserandBook(UserDAO.searchUser(tfUser.getText()).getId(),BookDAO.searchBook(title).getId(),dateEmprunt)).show();
		        	 }
		        	 else 
		        	 {
		        		 new Alert(Alert.AlertType.INFORMATION,"Book unavailable").show();
		        	 }
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }

		      

		        tfTitle.clear();
		        tfUser.clear();
		        pfPass.clear();
		        dpBorrow.setValue(null);
		        dpReturn.setValue(null);
		    });

		    // ----------- Formulaire ----------
		    VBox formBox = new VBox(15);
		    formBox.setPadding(new Insets(40));
		    formBox.setAlignment(Pos.TOP_CENTER);
		    formBox.setStyle("-fx-background-color: #f4f6fb; -fx-border-radius: 12;");
		    formBox.getChildren().addAll(
		        lblTitle, tfTitle,
		        lblUser, tfUser,
		        lblPass, pfPass,
		        lblDateBorrow, dpBorrow,
		        lblDateReturn, dpReturn,
		        btnBorrow
		    );

		    // ----------- Sidebar ----------
		    VBox sidebar = new VBox();
		    sidebar.setPrefWidth(200);
		    sidebar.setStyle("-fx-background-color: #1e1e2f;");

		    // ----------- Topbar ----------
		    HBox topbar = new HBox();
		    topbar.setPrefHeight(50);
		    topbar.setStyle("-fx-background-color: #1e1e2f;");

		    // ----------- Root ----------
		    BorderPane root = new BorderPane();
		    root.setCenter(formBox);
		    root.setLeft(sidebar);
		    root.setTop(topbar);
		    root.setLeft(vbox);
		    Scene scene = new Scene(root, 1000, 600);
		    primaryStage.setScene(scene);
		    primaryStage.setTitle("Borrow Book");
		    primaryStage.show();
		}


	
	
		private void showAddBook(Stage primaryStage,VBox vbox) {
		    // --- Labels et TextFields ---
		    Label lblTitle = new Label("Title *");
		    lblTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
		    TextField tfTitle = new TextField();
		    tfTitle.setPromptText("Enter book title");
		    tfTitle.setPrefWidth(300);
		    tfTitle.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");

		    Label lblAuthor = new Label("Author");
		    lblAuthor.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
		    TextField tfAuthor = new TextField();
		    tfAuthor.setPromptText("Enter author name (optional)");
		    tfAuthor.setPrefWidth(300);
		    tfAuthor.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");

		    Label lblLanguage = new Label("Language");
		    lblLanguage.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
		    TextField tfLanguage = new TextField();
		    tfLanguage.setPromptText("Enter language (optional)");
		    tfLanguage.setPrefWidth(300);
		    tfLanguage.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");

		    Label lblDescription = new Label("Description");
		    lblDescription.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
		    TextArea taDescription = new TextArea();
		    taDescription.setPromptText("Enter description (optional)");
		    taDescription.setPrefRowCount(3);
		    taDescription.setPrefWidth(300);
		    taDescription.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");

		    Label lblYear = new Label("Year of Edition");
		    lblYear.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
		    TextField tfYear = new TextField();
		    tfYear.setPromptText("Enter year (optional)");
		    tfYear.setPrefWidth(300);
		    tfYear.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");
		    

		    // --- Bouton ---
		    Button btnAdd = new Button("Add Book");
		    btnAdd.setPrefWidth(160);
		    btnAdd.setStyle("""
		        -fx-background-color: #3b82f6;
		        -fx-text-fill: white;
		        -fx-font-weight: bold;
		        -fx-background-radius: 10;
		    """);
		    btnAdd.setOnAction(e -> {
		        String title = tfTitle.getText().trim(); 
		        String author = tfAuthor.getText().trim();
		        String language = tfLanguage.getText().trim();
		        String description = taDescription.getText().trim();
		        int year = tfYear.getText().isEmpty() ? 0 : Integer.parseInt(tfYear.getText().trim());

		        if (title.isEmpty()) {
		            new Alert(Alert.AlertType.WARNING, "Title is required!").show();
		            return;
		        }

		        try {
		            BookDAO.insertBook(new Book(title, author, language, description, year));
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }

		        tfTitle.clear();
		        tfAuthor.clear();
		        tfLanguage.clear();
		        taDescription.clear();
		        tfYear.clear();

		        new Alert(Alert.AlertType.INFORMATION, "Book added successfully!").show();
		    });

		    // --- Formulaire ---
		    VBox formBox = new VBox(15);
		    formBox.setPadding(new Insets(40));
		    formBox.setAlignment(Pos.TOP_CENTER);
		    formBox.setStyle("-fx-background-color: #f4f6fb; -fx-border-radius: 12;");
		    formBox.getChildren().addAll(
		        lblTitle, tfTitle,
		        lblAuthor, tfAuthor,
		        lblLanguage, tfLanguage,
		        lblDescription, taDescription,
		        lblYear, tfYear,
		        btnAdd
		    );

		    // --- Sidebar et Topbar ---
		    VBox sidebar = new VBox();
		    sidebar.setPrefWidth(200);
		    sidebar.setStyle("-fx-background-color: #1e1e2f;");

		    HBox topbar = new HBox();
		    topbar.setPrefHeight(50);
		    topbar.setStyle("-fx-background-color: #1e1e2f;");
		    
		    // --- Root ---
		    BorderPane root = new BorderPane();
		    root.setCenter(formBox);
		    root.setLeft(sidebar);
		    root.setTop(topbar);
		    root.setLeft(vbox);
		    Scene scene = new Scene(root, 1000, 600);
		    primaryStage.setScene(scene);
		    primaryStage.setTitle("Add Book");
		    primaryStage.show();
		}

		private void showDelBook(Stage primaryStage ,VBox vbox) {
		    // --- Label et TextField ---
		    Label lblTitle = new Label("Title of the book to delete *");
		    lblTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
		    
		    TextField tfTitle = new TextField();
		    tfTitle.setPromptText("Enter book title");
		    tfTitle.setPrefWidth(300);
		    tfTitle.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");

		    // --- Bouton ---
		    Button btnDelete = new Button("Delete Book");
		    btnDelete.setPrefWidth(160);
		    btnDelete.setStyle("""
		        -fx-background-color: #3b82f6;
		        -fx-text-fill: white;
		        -fx-font-weight: bold;
		        -fx-background-radius: 10;
		    """);
		    btnDelete.setOnAction(e -> {
		        String title = tfTitle.getText().trim();
		        if (title.isEmpty()) {
		            new Alert(Alert.AlertType.WARNING, "Title is required!").show();
		            return;
		        }

		        try {
		            BookDAO.deleteBook(BookDAO.searchBook(title).getId());
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }

		        tfTitle.clear();
		        new Alert(Alert.AlertType.INFORMATION, "Book deleted successfully!").show();
		    });

		    // --- Formulaire ---
		    VBox formBox = new VBox(15);
		    formBox.setPadding(new Insets(40));
		    formBox.setAlignment(Pos.TOP_CENTER);
		    formBox.setStyle("-fx-background-color: #f4f6fb; -fx-border-radius: 12;");
		    formBox.getChildren().addAll(lblTitle, tfTitle, btnDelete);

		    // --- Sidebar et Topbar ---
		    VBox sidebar = new VBox();
		    sidebar.setPrefWidth(200);
		    sidebar.setStyle("-fx-background-color: #1e1e2f;");

		    HBox topbar = new HBox();
		    topbar.setPrefHeight(50);
		    topbar.setStyle("-fx-background-color: #1e1e2f;");
		    
		    // --- BorderPane principal ---
		    BorderPane root = new BorderPane();
		    root.setCenter(formBox);
		    root.setLeft(sidebar);
		    root.setTop(topbar);
		    root.setLeft(vbox);
		    Scene scene = new Scene(root, 1000, 600);
		    primaryStage.setScene(scene);
		    primaryStage.setTitle("Delete Book");
		    primaryStage.show();
		}
		
		
		private void showAddLibrarian(Stage primaryStage,VBox vbox) {

		    // ---------- Labels et Champs ----------
		    Label lblUser = new Label("Librarian Username *");
		    lblUser.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
		    TextField tfUser = new TextField();
		    tfUser.setPromptText("Enter librarian username");
		    tfUser.setPrefWidth(300);
		    tfUser.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");

		    Label lblPass = new Label("Password *");
		    lblPass.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
		    PasswordField pfPass = new PasswordField();
		    pfPass.setPromptText("Enter password");
		    pfPass.setPrefWidth(300);
		    pfPass.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");

		    // ---------- Bouton ----------
		    Button btnAdd = new Button("Add Librarian");
		    btnAdd.setPrefWidth(160);
		    btnAdd.setStyle("""
		        -fx-background-color: #3b82f6;
		        -fx-text-fill: white;
		        -fx-font-weight: bold;
		        -fx-background-radius: 10;
		    """);
		    btnAdd.setOnAction(e -> {
		        if (tfUser.getText().isEmpty() || pfPass.getText().isEmpty()) {
		            new Alert(Alert.AlertType.WARNING, "All fields are required").show();
		            return;
		        }

		        try {
		            UserDAO.insertLibrarian(new Librarian(tfUser.getText(), pfPass.getText()));
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }

		        new Alert(Alert.AlertType.INFORMATION, "Librarian added successfully").show();

		        tfUser.clear();
		        pfPass.clear();
		    });

		    // ---------- Formulaire ----------
		    VBox formBox = new VBox(15);
		    formBox.setAlignment(Pos.TOP_CENTER);
		    formBox.setPadding(new Insets(40));
		    formBox.setStyle("-fx-background-color: #f4f6fb; -fx-border-radius: 12;");
		    formBox.getChildren().addAll(
		        lblUser, tfUser,
		        lblPass, pfPass,
		        btnAdd
		    );

		    // ---------- Sidebar ----------
		    VBox sidebar = new VBox();
		    sidebar.setPrefWidth(200);
		    sidebar.setStyle("-fx-background-color: #1e1e2f;");

		    // ---------- Topbar ----------
		    HBox topbar = new HBox();
		    topbar.setPrefHeight(50);
		    topbar.setStyle("-fx-background-color: #1e1e2f;");
		    
		    // ---------- Root ----------
		    BorderPane root = new BorderPane();
		    root.setCenter(formBox);
		    root.setLeft(sidebar);
		    root.setTop(topbar);
		    root.setLeft(vbox);
		    Scene scene = new Scene(root, 1000, 600);
		    primaryStage.setScene(scene);
		    primaryStage.setTitle("Add Librarian");
		    primaryStage.show();
		}

		private void showDeleteLibrarian(Stage primaryStage,VBox vbox) {

		    // ---------- Label et TextField ----------
		    Label lblUser = new Label("Username of the librarian to delete *");
		    lblUser.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");

		    TextField tfUser = new TextField();
		    tfUser.setPromptText("Enter librarian username");
		    tfUser.setPrefWidth(300);
		    tfUser.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");

		    // ---------- Bouton ----------
		    Button btnDelete = new Button("Delete Librarian");
		    btnDelete.setPrefWidth(160);
		    btnDelete.setStyle("""
		        -fx-background-color: #3b82f6;
		        -fx-text-fill: white;
		        -fx-font-weight: bold;
		        -fx-background-radius: 10;
		    """);

		    btnDelete.setOnAction(e -> {
		        String username = tfUser.getText().trim();
		        if (username.isEmpty()) {
		            new Alert(Alert.AlertType.WARNING, "Username is required!").show();
		            return;
		        }

		        try {
		            UserDAO.deleteUser(UserDAO.searchUser(username).getId());
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }

		        new Alert(Alert.AlertType.INFORMATION, "Librarian deleted successfully").show();
		        tfUser.clear();
		    });

		    // ---------- Formulaire ----------
		    VBox formBox = new VBox(15);
		    formBox.setAlignment(Pos.TOP_CENTER);
		    formBox.setPadding(new Insets(40));
		    formBox.setStyle("-fx-background-color: #f4f6fb; -fx-border-radius: 12;");
		    formBox.getChildren().addAll(lblUser, tfUser, btnDelete);

		    // ---------- Sidebar ----------
		    VBox sidebar = new VBox();
		    sidebar.setPrefWidth(200);
		    sidebar.setStyle("-fx-background-color: #1e1e2f;");

		    // ---------- Topbar ----------
		    HBox topbar = new HBox();
		    topbar.setPrefHeight(50);
		    topbar.setStyle("-fx-background-color: #1e1e2f;");
		    
		    // ---------- Root ----------
		    BorderPane root = new BorderPane();
		    root.setCenter(formBox);
		    root.setLeft(sidebar);
		    root.setTop(topbar);
		    root.setLeft(vbox);
		    Scene scene = new Scene(root, 1000, 600);
		    primaryStage.setScene(scene);
		    primaryStage.setTitle("Delete Librarian");
		    primaryStage.show();
		}

		private void showModifyBook(Stage primaryStage,VBox vbox) {

		    // ---------- Labels et Champs ----------
		    Label lblTitle = new Label("Book Title *");
		    lblTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
		    TextField tfTitle = new TextField();
		    tfTitle.setPromptText("Enter book title (required)");
		    tfTitle.setPrefWidth(300);
		    tfTitle.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");

		    Label lblAuthor = new Label("New Author");
		    lblAuthor.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
		    TextField tfAuthor = new TextField();
		    tfAuthor.setPromptText("Leave empty to keep unchanged");
		    tfAuthor.setPrefWidth(300);
		    tfAuthor.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");

		    Label lblLanguage = new Label("New Language");
		    lblLanguage.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
		    TextField tfLanguage = new TextField();
		    tfLanguage.setPromptText("Leave empty to keep unchanged");
		    tfLanguage.setPrefWidth(300);
		    tfLanguage.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");

		    Label lblDescription = new Label("New Description");
		    lblDescription.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
		    TextArea taDescription = new TextArea();
		    taDescription.setPromptText("Leave empty to keep unchanged");
		    taDescription.setPrefRowCount(3);
		    taDescription.setPrefWidth(300);
		    taDescription.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");

		    Label lblYear = new Label("New Year of Edition");
		    lblYear.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
		    TextField tfYear = new TextField();
		    tfYear.setPromptText("Leave empty to keep unchanged");
		    tfYear.setPrefWidth(300);
		    tfYear.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");
		    Button btnsearch = new Button("Search");
		 
		    btnsearch.setOnAction(e->{
		    	Book book = null ; 
				try {
					book = BookDAO.searchBook(tfTitle.getText());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			

		    if (book == null) {
		        new Alert(Alert.AlertType.ERROR, "Book not found").show();
		        return;
		    }
		    tfAuthor.setText(book.getAuteur());
		    tfLanguage.setText(book.getLangue());
		    taDescription.setText(book.getDescription());
		    tfYear.setText(String.valueOf(book.getAnnee()));

		    
		    });
		    btnsearch.setPrefWidth(160);
		    btnsearch.setStyle("""
		        -fx-background-color: #3b82f6;
		        -fx-text-fill: white;
		        -fx-font-weight: bold;
		        -fx-background-radius: 10;
		    """);
		    
		    // ---------- Bouton ----------
		    Button btnModify = new Button("Modify Book");
		    btnModify.setPrefWidth(160);
		    btnModify.setStyle("""
		        -fx-background-color: #3b82f6;
		        -fx-text-fill: white;
		        -fx-font-weight: bold;
		        -fx-background-radius: 10;
		    """);
		    btnModify.setOnAction(e -> {
		        if (tfTitle.getText().trim().isEmpty()) {
		            new Alert(Alert.AlertType.WARNING, "Book title is required! Search it before ").show();
		            return;
		        }
		        else 
		        {
		        // Appel JDBC pour modifier le livre ici
		        try {
		        	
		        	
					BookDAO.updateBook(new Book(tfTitle.getText(),tfAuthor.getText(),tfLanguage.getText(),taDescription.getText(),Integer.parseInt( tfYear.getText())));
				} catch (NumberFormatException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

		        new Alert(Alert.AlertType.INFORMATION, "Book modified successfully").show();
		        }
		        tfTitle.clear();
		        tfAuthor.clear();
		        tfLanguage.clear();
		        taDescription.clear();
		        tfYear.clear();
		    });

		    // ---------- Formulaire ----------
		    VBox formBox = new VBox(15);
		    formBox.setAlignment(Pos.TOP_CENTER);
		    formBox.setPadding(new Insets(40));
		    formBox.setStyle("-fx-background-color: #f4f6fb; -fx-border-radius: 12;");
		    formBox.getChildren().addAll(
		        lblTitle, tfTitle,
		        lblAuthor, tfAuthor,
		        lblLanguage, tfLanguage,
		        lblDescription, taDescription,
		        lblYear, tfYear,btnsearch,
		        btnModify
		    );

		    // ---------- Sidebar ----------
		    VBox sidebar = new VBox();
		    sidebar.setPrefWidth(200);
		    sidebar.setStyle("-fx-background-color: #1e1e2f;");

		    // ---------- Topbar ----------
		    HBox topbar = new HBox();
		    topbar.setPrefHeight(50);
		    topbar.setStyle("-fx-background-color: #1e1e2f;");
		   
		    // ---------- Root ----------
		    BorderPane root = new BorderPane();
		    root.setCenter(formBox);
		    root.setLeft(sidebar);
		    root.setTop(topbar);
		    root.setLeft(vbox);
		    Scene scene = new Scene(root, 1000, 600);
		    primaryStage.setScene(scene);
		    primaryStage.setTitle("Modify Book");
		    primaryStage.show();
		}

		private void seeLibrarian(Stage primaryStage, List<Librarian> librarians,VBox vbox) {

		    // ---------- TilePane pour les cartes ----------
		    TilePane tilePane = new TilePane();
		    tilePane.setPadding(new Insets(30));
		    tilePane.setHgap(30);
		    tilePane.setVgap(30);
		    tilePane.setPrefColumns(4);
		    tilePane.setAlignment(Pos.TOP_CENTER);

		    // ---------- Image par défaut ----------
		    
		    Image image ; 
	        image = new Image(getClass().getResourceAsStream("/resources/icon.png"));

		    // ---------- Création des cartes ----------
		    for (Librarian librarian : librarians) {

		        ImageView imageView = new ImageView(image);
		        imageView.setFitWidth(120);
		        imageView.setFitHeight(120);
		        imageView.setPreserveRatio(true);

		        Label nameLabel = new Label(librarian.getUsername());
		        nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #111827;");
		        nameLabel.setWrapText(true);
		        nameLabel.setMaxWidth(120);
		        nameLabel.setAlignment(Pos.CENTER);

		        VBox card = new VBox(10);
		        card.setAlignment(Pos.CENTER);
		        card.setPadding(new Insets(15));
		        card.setStyle("""
		            -fx-background-color: #f4f6fb;
		            -fx-background-radius: 12;
		            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);
		        """);
		        card.getChildren().addAll(imageView, nameLabel);

		        tilePane.getChildren().add(card);
		    }

		    // ---------- ScrollPane ----------
		    ScrollPane scrollPane = new ScrollPane(tilePane);
		    scrollPane.setFitToWidth(true);
		    scrollPane.setStyle("-fx-background-color: transparent;");
		    scrollPane.setPadding(new Insets(20));

		    // ---------- Sidebar ----------
		    VBox sidebar = new VBox();
		    sidebar.setPrefWidth(200);
		    sidebar.setStyle("-fx-background-color: #1e1e2f;");

		    // ---------- Topbar ----------
		    HBox topbar = new HBox();
		    topbar.setPrefHeight(50);
		    topbar.setStyle("-fx-background-color: #1e1e2f;");
		    
		    // ---------- Root ----------
		    BorderPane root = new BorderPane();
		    root.setCenter(scrollPane);
		    root.setLeft(sidebar);
		    root.setTop(topbar);
		    root.setLeft(vbox);
		    Scene scene = new Scene(root, 1000, 600);
		    primaryStage.setScene(scene);
		    primaryStage.setTitle("Librarians Overview");
		    primaryStage.show();
		}
		private void seeUser(Stage primaryStage, List<User> users,VBox vbox) {

		    // ---------- TilePane pour les cartes ----------
		    TilePane tilePane = new TilePane();
		    tilePane.setPadding(new Insets(30));
		    tilePane.setHgap(30);
		    tilePane.setVgap(30);
		    tilePane.setPrefColumns(4);
		    tilePane.setAlignment(Pos.TOP_CENTER);

		    // ---------- Image par défaut ----------
		    
		    Image image ; 
	        image = new Image(getClass().getResourceAsStream("/resources/icon.png"));

		    // ---------- Création des cartes ----------
		    for (User user : users) {

		        ImageView imageView = new ImageView(image);
		        imageView.setFitWidth(120);
		        imageView.setFitHeight(120);
		        imageView.setPreserveRatio(true);

		        Label nameLabel = new Label(user.getUsername());
		        nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #111827;");
		        nameLabel.setWrapText(true);
		        nameLabel.setMaxWidth(120);
		        nameLabel.setAlignment(Pos.CENTER);

		        VBox card = new VBox(10);
		        card.setAlignment(Pos.CENTER);
		        card.setPadding(new Insets(15));
		        card.setStyle("""
		            -fx-background-color: #f4f6fb;
		            -fx-background-radius: 12;
		            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);
		        """);
		        card.getChildren().addAll(imageView, nameLabel);

		        tilePane.getChildren().add(card);
		    }

		    // ---------- ScrollPane ----------
		    ScrollPane scrollPane = new ScrollPane(tilePane);
		    scrollPane.setFitToWidth(true);
		    scrollPane.setStyle("-fx-background-color: transparent;");
		    scrollPane.setPadding(new Insets(20));

		    // ---------- Sidebar ----------
		    VBox sidebar = new VBox();
		    sidebar.setPrefWidth(200);
		    sidebar.setStyle("-fx-background-color: #1e1e2f;");

		    // ---------- Topbar ----------
		    HBox topbar = new HBox();
		    topbar.setPrefHeight(50);
		    topbar.setStyle("-fx-background-color: #1e1e2f;");
		    
		    // ---------- Root ----------
		    BorderPane root = new BorderPane();
		    root.setCenter(scrollPane);
		    root.setLeft(sidebar);
		    root.setTop(topbar);
		    root.setLeft(vbox);
		    Scene scene = new Scene(root, 1000, 600);
		    primaryStage.setScene(scene);
		    primaryStage.setTitle("Librarians Overview");
		    primaryStage.show();
		}
		private void showReturnBook(Stage primaryStage,VBox vbox) {

		    // ---------- Labels et Champs ----------
		    Label lblId = new Label("Borrow ID *");
		    lblId.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
		    TextField tfId = new TextField();
		    tfId.setPromptText("Enter borrow ID");
		    tfId.setPrefWidth(300);
		    tfId.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");

		    Label lblUser = new Label("Username *");
		    lblUser.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
		    TextField tfUser = new TextField();
		    tfUser.setPromptText("Enter username");
		    tfUser.setPrefWidth(300);
		    tfUser.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");

		    Label lblPass = new Label("Password *");
		    lblPass.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
		    PasswordField pfPass = new PasswordField();
		    pfPass.setPromptText("Enter password");
		    pfPass.setPrefWidth(300);
		    pfPass.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");

		    // ---------- Bouton ----------
		    Button btnReturn = new Button("Return Book");
		    btnReturn.setPrefWidth(160);
		    btnReturn.setStyle("""
		        -fx-background-color: #3b82f6;
		        -fx-text-fill: white;
		        -fx-font-weight: bold;
		        -fx-background-radius: 10;
		    """);
		    btnReturn.setOnAction(e -> {

		        if (tfId.getText().trim().isEmpty() ||
		            tfUser.getText().trim().isEmpty() ||
		            pfPass.getText().trim().isEmpty()
		        ) {
		            new Alert(Alert.AlertType.WARNING, "All fields are required").show();
		            return;
		        }

		        int idEmprunt = Integer.parseInt(tfId.getText().trim());
		        String username = tfUser.getText().trim();
		        String password = pfPass.getText().trim();

		        // Appel JDBC pour retour
		        try {
		            BookEmpruntDAO.retournerLivre(idEmprunt);
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }

		        new Alert(Alert.AlertType.INFORMATION, "Book returned successfully").show();

		        tfId.clear();
		        tfUser.clear();
		        pfPass.clear();
		    });

		    // ---------- Formulaire ----------
		    VBox formBox = new VBox(15);
		    formBox.setAlignment(Pos.TOP_CENTER);
		    formBox.setPadding(new Insets(40));
		    formBox.setStyle("-fx-background-color: #f4f6fb; -fx-border-radius: 12;");
		    formBox.getChildren().addAll(
		        lblId, tfId,
		        lblUser, tfUser,
		        lblPass, pfPass,
		        btnReturn
		    );

		    // ---------- Sidebar ----------
		    VBox sidebar = new VBox();
		    sidebar.setPrefWidth(200);
		    sidebar.setStyle("-fx-background-color: #1e1e2f;");

		    // ---------- Topbar ----------
		    HBox topbar = new HBox();
		    topbar.setPrefHeight(50);
		    topbar.setStyle("-fx-background-color: #1e1e2f;");
		    
		    // ---------- Root ----------
		    BorderPane root = new BorderPane();
		    root.setCenter(formBox);
		    root.setLeft(sidebar);
		    root.setTop(topbar);
		    root.setLeft(vbox);
		    Scene scene = new Scene(root, 1000, 600);
		    primaryStage.setScene(scene);
		    primaryStage.setTitle("Return Book");
		    primaryStage.show();
		}
		private void showOneBook(Stage primaryStage,Book book,VBox vbox )
		{
			
			
			VBox sidebar = new VBox();
		    sidebar.setPrefWidth(200);
		    sidebar.setStyle("-fx-background-color: #1e1e2f;");

		    // ---------- Topbar ----------
		    HBox topbar = new HBox();
		    topbar.setPrefHeight(50);
		    topbar.setStyle("-fx-background-color: #1e1e2f;");
		   

		    // ---------- Root ----------
		    BorderPane root = new BorderPane();
		    
		    
	        Image image ; 
	        image = new Image(getClass().getResourceAsStream("/resources/livre-video.png"));
	        

	        
			ImageView imageView = new ImageView(image);
	        imageView.setFitWidth(300);
	        imageView.setFitHeight(400);
	        imageView.setPreserveRatio(false); // garde le ratio
	        imageView.setSmooth(true);       // lisse l’image

		    
		    
		    VBox detailsBook = new VBox(10);
		    
		    Label lbAuthor = new Label("Author");
		    lbAuthor.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
		    Text txtAuthor = new Text(book.getAuteur());
		    txtAuthor.setStyle("""
		    	    -fx-font-size: 14px;
		    	    -fx-fill: #666666;
		    	""");
		    Label lblLanguage = new Label("Language");
		    lblLanguage.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
		    Text txtLanguage = new Text(book.getLangue());
		    txtLanguage.setStyle("""
		    	    -fx-font-size: 14px;
		    	    -fx-fill: #666666;
		    	""");
		    Label lblDescription = new Label("Description");
		    lblDescription.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
		    TextArea  txtDescription = new TextArea(book.getDescription());
		    txtDescription.setEditable(false);
		    txtDescription.setWrapText(true);
		    txtDescription.setEditable(false);
		    txtDescription.setFocusTraversable(false);
		    
		   
		    Label lblTitle = new Label("Title");
		    lblTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
		    Text txtTitle = new Text(book.getTitre());
		    txtTitle.setStyle("""
		    	    -fx-font-size: 14px;
		    	    -fx-fill: #666666;
		    	""");
		    Label lblAvailable = new Label("Disponibilite");
		    lblAvailable.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
		    Text txtAvailable = new Text("null");
			try {
				txtAvailable = new Text(BookEmpruntDAO.searchBookAvailable(BookDAO.searchBook(book.getTitre()).getId()));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    txtAvailable.setStyle("""
		    	    -fx-font-size: 14px;
		    	    -fx-fill: #666666;
		    	""");
		    detailsBook.getChildren().addAll(lblTitle,txtTitle,lbAuthor,txtAuthor,lblLanguage,txtLanguage,lblDescription,txtDescription
		    		,lblAvailable,txtAvailable);
		    //root.setRight(detailsBook);
		    //detailsBook.setPadding(new Insets(100,0,0,500));
		    detailsBook.setSpacing(10);
		    detailsBook.setPadding(new Insets(40, 0, 0, 100));
		    HBox centerBox = new HBox(30); // 30px d'espace entre image et détails
		    centerBox.setPadding(new Insets(50, 20, 50, 20)); // padding général
		    centerBox.getChildren().addAll(imageView, detailsBook);
		    centerBox.setAlignment(Pos.CENTER_LEFT);
		    
		    root.setCenter(centerBox);

		    root.setLeft(sidebar);
		    root.setTop(topbar);
		    root.setLeft(vbox);
		    root.setStyle("-fx-background-color: #f4f6fb;");

		    Scene scene = new Scene(root, 1000, 600);
		    primaryStage.setScene(scene);
		    primaryStage.setTitle("Library Catalog");
		    primaryStage.show();
		}


		private void showBook(Stage primaryStage, List<Book> books,VBox vbox) {

		    // ---------- TilePane (catalogue) ----------
		    TilePane tilePane = new TilePane();
		    tilePane.setPadding(new Insets(30));
		    tilePane.setHgap(30);
		    tilePane.setVgap(30);
		    tilePane.setPrefColumns(4); // nombre de livres par ligne
		    tilePane.setAlignment(Pos.TOP_CENTER);

		    // ---------- Création des cartes livres ----------
		    for (Book book : books) {

		        Image image;
		        
		        	 
			    image = new Image(getClass().getResourceAsStream("/resources/livre-video.png"));
		      

		        ImageView imageView = new ImageView(image);
		        imageView.setFitWidth(150);
		        imageView.setFitHeight(200);
		        imageView.setPreserveRatio(true);

		        Label titleLabel = new Label(book.getTitre());
		        titleLabel.setWrapText(true);
		        titleLabel.setMaxWidth(150);
		        titleLabel.setAlignment(Pos.CENTER);
		        titleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #111827;");

		        // Card design amélioré
		        VBox bookCard = new VBox(10);
		        bookCard.setAlignment(Pos.CENTER);
		        bookCard.setPadding(new Insets(10));
		        bookCard.setStyle("""
		            -fx-background-color: white;
		            -fx-background-radius: 10;
		            -fx-border-radius: 10;
		            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);
		        """);
		        // Details cachés 
		        Label author = new Label (book.getAuteur());
		        
		        Label year = new Label("2008");
		        Label desc = new Label(book.getDescription());
		        VBox details = new VBox(author,year,desc);
		        details.setPadding(new Insets(10,0,0,0));
		        details.setVisible(false);
		        details.setManaged(false);
		        details.setOpacity(0);
		        details.setMaxHeight(0);
		        details.setMinHeight(0);
		        details.setPrefHeight(0);
		        bookCard.setOnMouseClicked(e->{
		        	InputStream testStream = getClass().getResourceAsStream("/resources/image_0.jpg");
		        	System.out.println(testStream != null); // doit afficher true

		        	showOneBook(primaryStage,book,vbox);
		        });

		        // Hover effect
		        bookCard.setOnMouseEntered(e -> bookCard.setStyle("""
		            -fx-background-color: #e0e7ff;
		            -fx-background-radius: 10;
		            -fx-border-radius: 10;
		            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 8, 0, 0, 4);
		        """));
		        bookCard.setOnMouseExited(e -> bookCard.setStyle("""
		            -fx-background-color: white;
		            -fx-background-radius: 10;
		            -fx-border-radius: 10;
		            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);
		        """));

		        bookCard.getChildren().addAll(imageView, titleLabel);
		        tilePane.getChildren().addAll(new VBox(bookCard));
		        

		    }

		    // ---------- Scroll ----------
		    ScrollPane scrollPane = new ScrollPane(tilePane);
		    scrollPane.setFitToWidth(true);
		    scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

		    // ---------- Sidebar ----------
		    VBox sidebar = new VBox();
		    sidebar.setPrefWidth(200);
		    sidebar.setStyle("-fx-background-color: #1e1e2f;");

		    // ---------- Topbar ----------
		    HBox topbar = new HBox();
		    topbar.setPrefHeight(50);
		    topbar.setStyle("-fx-background-color: #1e1e2f;");
		    

		    // ---------- Root ----------
		    BorderPane root = new BorderPane();

		    

		    root.setCenter(scrollPane);
		    root.setLeft(sidebar);
		    root.setTop(topbar);
		    root.setLeft(vbox);
		    root.setStyle("-fx-background-color: #f4f6fb;");

		    Scene scene = new Scene(root, 1000, 600);
		    primaryStage.setScene(scene);
		    primaryStage.setTitle("Library Catalog");
		    primaryStage.show();
		}
		private void showBookBorrowed(Stage primaryStage,User user,VBox vbox) {

		    // ---------- TilePane (catalogue) ----------
		    TilePane tilePane = new TilePane();
		    tilePane.setPadding(new Insets(30));
		    tilePane.setHgap(30);
		    tilePane.setVgap(30);
		    tilePane.setPrefColumns(4); // nombre de livres par ligne
		    tilePane.setAlignment(Pos.TOP_CENTER);
		    List<Emprunt> emprunts = new ArrayList<Emprunt>();
		    try {
				emprunts=BookEmpruntDAO.getEmpruntsByUser(UserDAO.searchUser(user.getUsername()).getId());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    // ---------- Création des cartes livres ----------
		    for (Emprunt emprunt : emprunts ) {

		        Image image;
		        
		        	 
			    image = new Image(getClass().getResourceAsStream("/resources/livre-video.png"));
		      

		        ImageView imageView = new ImageView(image);
		        imageView.setFitWidth(150);
		        imageView.setFitHeight(200);
		        imageView.setPreserveRatio(true);

		        Label titleLabel = new Label(emprunt.getBook().getTitre());
		        titleLabel.setWrapText(true);
		        titleLabel.setMaxWidth(150);
		        titleLabel.setAlignment(Pos.CENTER);
		        titleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #111827;");

		        // Card design amélioré
		        VBox bookCard = new VBox(10);
		        bookCard.setAlignment(Pos.CENTER);
		        bookCard.setPadding(new Insets(10));
		        bookCard.setStyle("""
		            -fx-background-color: white;
		            -fx-background-radius: 10;
		            -fx-border-radius: 10;
		            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);
		        """);
		        // Details cachés 
		        Label author = new Label (emprunt.getBook().getAuteur());
		        
		        Label year = new Label("2008");
		        Label desc = new Label(emprunt.getBook().getDescription());
		        VBox details = new VBox(author,year,desc);
		        details.setPadding(new Insets(10,0,0,0));
		        details.setVisible(false);
		        details.setManaged(false);
		        details.setOpacity(0);
		        details.setMaxHeight(0);
		        details.setMinHeight(0);
		        details.setPrefHeight(0);
		        bookCard.setOnMouseClicked(e->{
		        	InputStream testStream = getClass().getResourceAsStream("/resources/image_0.jpg");
		        	System.out.println(testStream != null); // doit afficher true

		        	showOneBook(primaryStage,emprunt.getBook(),vbox);
		        });

		        // Hover effect
		        bookCard.setOnMouseEntered(e -> bookCard.setStyle("""
		            -fx-background-color: #e0e7ff;
		            -fx-background-radius: 10;
		            -fx-border-radius: 10;
		            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 8, 0, 0, 4);
		        """));
		        bookCard.setOnMouseExited(e -> bookCard.setStyle("""
		            -fx-background-color: white;
		            -fx-background-radius: 10;
		            -fx-border-radius: 10;
		            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);
		        """));

		        bookCard.getChildren().addAll(imageView, titleLabel);
		        tilePane.getChildren().addAll(new VBox(bookCard));
		        

		    }

		    // ---------- Scroll ----------
		    ScrollPane scrollPane = new ScrollPane(tilePane);
		    scrollPane.setFitToWidth(true);
		    scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

		    // ---------- Sidebar ----------
		    VBox sidebar = new VBox();
		    sidebar.setPrefWidth(200);
		    sidebar.setStyle("-fx-background-color: #1e1e2f;");

		    // ---------- Topbar ----------
		    HBox topbar = new HBox();
		    topbar.setPrefHeight(50);
		    topbar.setStyle("-fx-background-color: #1e1e2f;");
		    

		    // ---------- Root ----------
		    BorderPane root = new BorderPane();

		    

		    root.setCenter(scrollPane);
		    root.setLeft(sidebar);
		    root.setTop(topbar);
		    root.setLeft(vbox);
		    root.setStyle("-fx-background-color: #f4f6fb;");

		    Scene scene = new Scene(root, 1000, 600);
		    primaryStage.setScene(scene);
		    primaryStage.setTitle("	Book Borrowed");
		    primaryStage.show();
		}
	private Rectangle createRect() {
	    Rectangle r = new Rectangle(160, 160, Color.WHITE);
	    r.setArcWidth(40);
	    r.setArcHeight(40);
	    r.setStroke(Color.BLACK);
	    r.setOpacity(100);
	    return r;
	}
	private VBox createAdminCard(
	        
	        String title,
	        String description,
	        EventHandler<ActionEvent> action
	) {
	    VBox card = new VBox(10);
	    card.setPadding(new Insets(20));
	    card.setPrefSize(200, 140);
	    card.setStyle("""
	        -fx-background-color: white;
	        -fx-background-radius: 14;
	        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.12), 10, 0, 0, 4);
	    """);

	   

	    Text t = new Text(title);
	    t.setFont(Font.font("Arial", FontWeight.BOLD, 16));

	    Text d = new Text(description);
	    d.setFill(Color.GRAY);

	    card.getChildren().addAll(t, d);

	    card.setOnMouseClicked(e -> action.handle(null));

	    card.setOnMouseEntered(e ->
	        card.setStyle("""
	            -fx-background-color: #eef1ff;
	            -fx-background-radius: 14;
	            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 12, 0, 0, 6);
	        """)
	    );

	    card.setOnMouseExited(e ->
	        card.setStyle("""
	            -fx-background-color: white;
	            -fx-background-radius: 14;
	            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.12), 10, 0, 0, 4);
	        """)
	    );

	    return card;
	}

	private Button createSidebarButton(String text, EventHandler<ActionEvent> action) {

	    Button b = new Button(text);
	    b.setMaxWidth(Double.MAX_VALUE);
	    b.setPrefHeight(42);
	    b.setAlignment(Pos.CENTER_LEFT);
	    b.setOnAction(action);

	    b.setStyle("""
	        -fx-background-color: transparent;
	        -fx-text-fill: #cccccc;
	        -fx-font-size: 14;
	    """);

	    b.setOnMouseEntered(e -> b.setStyle("""
	        -fx-background-color: #2c2c44;
	        -fx-text-fill: white;
	        -fx-font-size: 14;
	    """));

	    b.setOnMouseExited(e -> b.setStyle("""
	        -fx-background-color: transparent;
	        -fx-text-fill: #cccccc;
	        -fx-font-size: 14;
	    """));

	    return b;
	}

	public StackPane createActionPane(Rectangle rect, String labelText) {
	    // Créer le texte
	    Text text = new Text(labelText);
	    text.setFill(Color.BLACK); // couleur du texte
	    text.setFont(Font.font(20)); // taille du texte (modifiable)

	    // Créer le StackPane et ajoute les éléments
	    StackPane stack = new StackPane();
	    stack.getChildren().addAll(rect, text);

	    // Optionnel : effet visuel au survol
	    stack.setOnMouseEntered(e -> rect.setOpacity(0.8));
	    stack.setOnMouseExited(e -> rect.setOpacity(1.0));

	    return stack;
	}
	

	public static void main(String[] args) {
		launch(args);
	}
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        