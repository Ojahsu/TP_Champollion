package champollion;

public class ExceptionServiceDepasse extends Exception {

    private static final long serialVersionUID = 1L;

    public ExceptionServiceDepasse(UE ue) {
        super("Service depassé dans l'UE " + ue.getIntitule());
    }

    public ExceptionServiceDepasse() {
        super("Service depassé dans l'UE ");
    }

}
