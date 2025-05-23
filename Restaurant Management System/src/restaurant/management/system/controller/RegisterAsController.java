
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import restaurant.management.system.controller.CustomerRegistrationController;
import restaurant.management.system.view.CustomerRegistrationView;
import restaurant.management.system.view.RegisterAsView;


public class RegisterAsController {
    private RegisterAsView registerAsView = new RegisterAsView();

    public RegisterAsController(RegisterAsView registerAsView) {
        this.registerAsView = registerAsView;
    
        RegisterNavigation registerNavigation = new RegisterNavigation();

        this.registerAsView.addOwnerAndStaffNavigation(registerNavigation);
        this.registerAsView.addCustomerNavigation(registerNavigation);
    }

    void open() {
        this.registerAsView.setVisible(true);
    }

    void close() {
        this.registerAsView.dispose();
    }

    class RegisterNavigation implements ActionListener {
        @Override
        
        public void actionPerformed(ActionEvent e) {
            CustomerRegistrationView customeregistrationView = new CustomerRegistrationView();
            CustomerRegistrationController customerregister = new CustomerRegistrationController(customeregistrationView);
            customerregister.open();
            close();
        }
    }
}

    

 
