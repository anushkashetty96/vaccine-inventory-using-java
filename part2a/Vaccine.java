package part2.part2a;

public class Vaccine {

    private VaccineBrand brand;
    private double dose;
    private String expiry_date;
    private long id;
    private double price;

    static int count=0;

    public Vaccine() {
        this.id=++count;
        this.brand=VaccineBrand.PFIZER;
        this.dose=1;
        this.expiry_date="2030";
        this.price=100;
    }

    public Vaccine(VaccineBrand brand, double dose, String expiry_date, double price)
    {
        this.id=++count;
        this.brand=brand;
        this.dose=dose;
        this.expiry_date=expiry_date;
        this.price=price;
    }

    public Vaccine(Vaccine vaccine)
    {
        this.id=++count;
        this.brand=vaccine.brand;
        this.dose=vaccine.dose;
        this.expiry_date=vaccine.expiry_date;
        this.price=vaccine.price;
    }

    public void setBrand(VaccineBrand brand) {
        this.brand = brand;
    }

    public void setDose(double dose) {
        this.dose = dose;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public VaccineBrand getBrand() {
        return brand;
    }

    public double getDose() {
        return dose;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public static int findNumberOfCreatedVaccines() {
        return count;
    }

    @Override
    public String toString() {
        return
                "ID : " + id +"\n"+
                "Brand : " + brand +"\n"+
                "Dose : " + dose +"\n"+
                "Expiry : "+expiry_date+"\n"+
                "Price : " + price+"\n";
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = (int) (37*hash+this.dose);
        hash = (int)(37*hash+this.price);
        hash = 37*hash+expiry_date.hashCode();
        hash = 37*hash+brand.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj){
        if(obj==this)
            return true;
        if(!(obj instanceof Vaccine))
            return false;
        Vaccine vaccine = (Vaccine) obj;
        return vaccine.brand==this.brand && vaccine.dose==this.dose;
    }
}

