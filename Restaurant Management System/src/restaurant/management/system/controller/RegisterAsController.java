
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import restaurant.management.system.controller.RegisterOwnerStaffController;
import restaurant.management.system.view.RegisterAsView;
import restaurant.management.system.view.RegisterOwnerStaffView;


public class RegisterAsController {
    private RegisterAsView registerAsView = new RegisterAsView();

    public RegisterAsController(RegisterAsView registerAsView) {
        this.registerAsView = registerAsView;
        this.registerAsView.addOwnerStaffNavigation(new OwnerStaffNavigation());
        
    }

    void open() {
        this.registerAsView.setVisible(true);
    }

    void close() {
        this.registerAsView.dispose();
    }

    class OwnerStaffNavigation implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            RegisterOwnerStaffView ownerStaffView = new RegisterOwnerStaffView();
            RegisterOwnerStaffController controller = new RegisterOwnerStaffController(ownerStaffView);
            controller.open();
            close();
        }
    }
    
    

 
