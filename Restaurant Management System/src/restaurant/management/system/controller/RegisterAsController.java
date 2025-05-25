
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import restaurant.management.system.controller.RegisterCustomerController;
import restaurant.management.system.view.RegisterCustomerView;
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
            RegisterCustomerView customeregistrationView = new RegisterCustomerView();
            RegisterCustomerController customerregister = new RegisterCustomerController(customeregistrationView);
            customerregister.open();
            close();
        }
    }
}

    

 
