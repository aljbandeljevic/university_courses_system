package Validations;

import DatabaseObjects.IDOfProgramDatabase;

import javax.swing.*;

public class ProgramValidations {
    private IDOfProgramDatabase programDAO;

    public ProgramValidations() {
        programDAO = new IDOfProgramDatabase();
    }

    private boolean validateID(int id) {
        return id > 0;
    }

    public String getProgramNameByID(int id) {
        if (validateID(id)) {
            return programDAO.getProgramNameByID(id);
        }

        JOptionPane.showMessageDialog(null, "Program ID invalid");
        return null;
    }

    public boolean addProgramIfNotExists(String program) {
        if (!programDAO.checkIfExists(program)) {
            return programDAO.addProgram(program);

        }

        return true;
    }

    public int getProgramIDByProgramName(String programName) {
        return programDAO.getProgramIDByProgramName(programName);
    }
}
