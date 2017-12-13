class Experiment {
}

long countOfPackages() throws ClassNotFoundException {
        return Experiment.class.getPackage().getPackages().length;
}

System.out.println("[ Q ] How many packages do we have..");
System.out.println(countOfPackages());
/exit
