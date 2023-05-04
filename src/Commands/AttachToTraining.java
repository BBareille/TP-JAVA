package Commands;

import Console.Command;
import DAL.DAL;
import database.SqlConnection;
import models.Training;

import java.util.Scanner;

public class AttachToTraining extends Command<Object, String> {

    private DAL modelToAttach;
    public AttachToTraining(Integer id, String displayName, SqlConnection connection, DAL model, DAL modelToAttach) {
        super(id, displayName, connection, model);
        this.modelToAttach = modelToAttach;
    }

    @Override
    public String apply(Object o) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ID de votre formation cible : ");
        Integer id_training = scanner.nextInt();
        try {
            if(model.findOne(id_training) == null){
                System.out.println("Cette entité n'existe pas");
            } else {
                Training training = (Training) model.findOne(id_training);
                System.out.println("La formation séléctionné : "+training);
                System.out.println("Id de"+ modelToAttach +" a attacher: ");
                Long modelToAttach_id = scanner.nextLong();
                if(modelToAttach.getClass().getSimpleName().equals("Former")){
                    training.attachFormer(modelToAttach_id);
                } else if (modelToAttach.getClass().getSimpleName().equals("Trainee")) {
                    training.attachTrainee(modelToAttach_id);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
