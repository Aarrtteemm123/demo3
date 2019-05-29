package com.example.demo.Controller;

import com.example.demo.DAO.*;
import com.example.demo.Model.*;
import com.example.demo.tableForm.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    //mysql://bcb531111f11df:3832f169@us-cdbr-iron-east-02.cleardb.net/heroku_e84d4f31db6940a?reconnect=true
    private final String url = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/heroku_e84d4f31db6940a";
    private final String user = "bcb531111f11df";
    private final String passwordDb = "3832f169";
    private int globalBufferId = 0;
    private boolean flagFilter = false;
    private Boolean flagAccess = null;
    private TableController tableController = new TableController(url, user, passwordDb);
    private PasswordDAO passwordDAO = new PasswordDAO(url, user, passwordDb);
    private StadiumDAO stadiumDAO = new StadiumDAO(url, user, passwordDb);
    private ManegeDAO manegeDAO = new ManegeDAO(url, user, passwordDb);
    private SportGymDAO sportGymDAO = new SportGymDAO(url, user, passwordDb);
    private CourtDAO courtDAO = new CourtDAO(url, user, passwordDb);
    private SwimmingPoolDAO swimmingPoolDAO = new SwimmingPoolDAO(url, user, passwordDb);
    private TrackDAO trackDAO = new TrackDAO(url, user, passwordDb);
    private BoardGamesDAO boardGamesDAO = new BoardGamesDAO(url, user, passwordDb);
    private IceRinkDAO iceRinkDAO = new IceRinkDAO(url, user, passwordDb);
    private TrainerDAO trainerDAO = new TrainerDAO(url, user, passwordDb);
    private SportDAO sportDAO = new SportDAO(url, user, passwordDb);
    private SportsmenDAO sportsmenDAO = new SportsmenDAO(url, user, passwordDb);
    private ClubDAO clubDAO = new ClubDAO(url, user, passwordDb);
    private HistoryDAO historyDAO = new HistoryDAO(url, user, passwordDb);
    private List<Stadium> bufferStadiumList = new ArrayList<>();
    private List<Manege> bufferManegeList = new ArrayList<>();
    private List<SportGym> bufferSportGymList = new ArrayList<>();
    private List<Court> bufferCourtList = new ArrayList<>();
    private List<SwimmingPool> bufferSwimmingPoolList = new ArrayList<>();
    private List<Track> bufferTrackList = new ArrayList<>();
    private List<BoardGames> bufferBoardGamesList = new ArrayList<>();
    private List<IceRink> bufferIceRinkList = new ArrayList<>();
    private List<SportsmenForm> bufferSportsmenFormList = new ArrayList<>();
    private List<TrainerForm> bufferTrainerFormList = new ArrayList<>();
    private List<HistoryForm> bufferHistoryFormList = new ArrayList<>();

    public MainController() throws SQLException {
    }

    public Model setAccess(Model model) {
        if (flagAccess) {
            Status status = new Status("Admin");
            model.addAttribute("status", status);
        } else {
            Status status = new Status("User");
            model.addAttribute("status", status);
        }
        return model;
    }

    //--------------------Info--------------------------------------------------------------

    @RequestMapping(value = "/task", method = RequestMethod.GET)
    public String getTask(Model model) {
        return "task";
    }

    @RequestMapping(value = "/document", method = RequestMethod.GET)
    public String getDocument(Model model) {
        return "document";
    }

    //-------------------------------------------------------------------------------------

    //-------------------Password----------------------------------------------------------

    @RequestMapping(value = "/title", method = RequestMethod.POST)
    public String checkPassword(@ModelAttribute QueryForm queryForm, Model model) throws SQLException {
        flagAccess = null;
        if (passwordDAO.readPassword(queryForm.getString(), queryForm.getString2()) != null && passwordDAO.readPassword(queryForm.getString(), queryForm.getString2())) {
            flagAccess = queryForm.getString().equals("admin");
            return "redirect:/sportsmen/list";
        }
        return "redirect:/title";
    }

    @RequestMapping(value = "/title", method = RequestMethod.GET)
    public String inputPassword(Model model) {
        flagAccess = null;
        QueryForm queryForm = new QueryForm();
        model.addAttribute("queryForm", queryForm);
        return "title";
    }

    //-------------------------------------------------------------------------------------


    //------------Stadium------------------------------------------------------------------

    @RequestMapping(value = "/stadium/list", method = RequestMethod.GET)
    public String getStadiums(Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        model = setAccess(model);
        if (flagFilter == false) {
            QueryForm queryForm = new QueryForm();
            model.addAttribute("stadiums", bufferStadiumList);
            model.addAttribute("queryForm", queryForm);
            flagFilter = true;
        } else {
            List<Stadium> stadiums = stadiumDAO.getAllStadium();
            QueryForm queryForm = new QueryForm();
            model.addAttribute("stadiums", stadiums);
            model.addAttribute("queryForm", queryForm);
        }
        return "List/stadiumList";
    }

    @RequestMapping(value = "/stadium/list", method = RequestMethod.POST)
    public String filterStadiums(@ModelAttribute QueryForm queryForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        List<Stadium> result = new ArrayList<>(100);
        flagFilter = false;
        if (queryForm.getString().equals("") && queryForm.getNum() == 0) {
            return "redirect:/stadium/list";
        } else if (!(queryForm.getString().equals("")) && queryForm.getNum() == 0) {
            stadiumDAO.getAllStadium().stream().filter(stadium -> stadium.getType().equals(queryForm.getString())).forEach(result::add);
            bufferStadiumList = result;
            return "redirect:/stadium/list";
        } else if (queryForm.getString().equals("") && queryForm.getNum() != 0) {
            stadiumDAO.getAllStadium().stream().filter(stadium -> stadium.getNumberOfViewers() < queryForm.getNum()).forEach(result::add);
            bufferStadiumList = result;
            return "redirect:/stadium/list";
        } else if (!(queryForm.getString().equals("")) && queryForm.getNum() != 0) {
            stadiumDAO.getAllStadium().stream().filter(stadium -> stadium.getNumberOfViewers() < queryForm.getNum() && stadium.getType().equals(queryForm.getString())).forEach(result::add);
            bufferStadiumList = result;
            return "redirect:/stadium/list";
        }
        return "redirect:/stadium/list";
    }

    @RequestMapping(value = "/stadium/add", method = RequestMethod.GET)
    public String addStadium(Model model) {
        if (flagAccess == null)
            return "redirect:/title";
        Stadium form = new Stadium();
        model.addAttribute("form", form);
        return "Form/stadiumForm";
    }

    @RequestMapping(value = "/stadium/add", method = RequestMethod.POST)
    public String saveStadium(@ModelAttribute Stadium stadiumForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess && tableController.checkIdInTable("stadium", stadiumForm.getId()) && tableController.checkAddressInTable("stadium", stadiumForm.getAddress()) && stadiumForm.getNumberOfViewers() > 0) {
            stadiumDAO.saveStadium(stadiumForm);
            return "redirect:/stadium/list";
        } else
            return "redirect:/stadium/add";
    }

    @RequestMapping(value = "/stadium/edit/{id}", method = RequestMethod.GET)
    public String editStadium(Model model, @PathVariable Integer id) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        Stadium stadium = stadiumDAO.getStadiumById(id);
        globalBufferId = id;
        model.addAttribute("stadium", stadium);
        return "Edit/editStadium";
    }

    @RequestMapping(value = "/stadium/edit", method = RequestMethod.POST)
    public String saveEditStadium(@ModelAttribute Stadium stadium, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess && tableController.checkUpdateAddress("stadium", stadium.getAddress(), globalBufferId) && stadium.getNumberOfViewers() > 0) {
            stadium.setId(globalBufferId);
            stadiumDAO.updateStadium(stadium);
        }
        return "redirect:/stadium/list";
    }


    @RequestMapping(value = "/stadium/delete/{id}", method = RequestMethod.GET)
    public String deleteStadium(Model model, @PathVariable Integer id) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess)
            stadiumDAO.deleteStadium(id);
        return "redirect:/stadium/list";
    }

    //-----------------------------------------------------------------------------------


    //------------manege-----------------------------------------------------------------

    @RequestMapping(value = "/manege/list", method = RequestMethod.GET)
    public String getManege(Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        model = setAccess(model);
        if (flagFilter == false) {
            QueryForm queryForm = new QueryForm();
            model.addAttribute("maneges", bufferManegeList);
            model.addAttribute("queryForm", queryForm);
            flagFilter = true;
        } else {
            List<Manege> maneges = manegeDAO.getAllManege();
            QueryForm queryForm = new QueryForm();
            model.addAttribute("maneges", maneges);
            model.addAttribute("queryForm", queryForm);
        }
        return "List/manegeList";
    }

    @RequestMapping(value = "/manege/list", method = RequestMethod.POST)
    public String filterManege(@ModelAttribute QueryForm queryForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        List<Manege> result = new ArrayList<>(100);
        flagFilter = false;
        if (queryForm.getString().equals("")) {
            return "redirect:/manege/list";
        } else if (!(queryForm.getString().equals(""))) {
            manegeDAO.getAllManege().stream().filter(manege -> manege.getTypeOfCover().equals(queryForm.getString())).forEach(result::add);
            bufferManegeList = result;
            return "redirect:/manege/list";
        }
        return "redirect:/manege/list";
    }

    @RequestMapping(value = "/manege/add", method = RequestMethod.GET)
    public String addManege(Model model) {
        if (flagAccess == null)
            return "redirect:/title";
        Manege manegeForm = new Manege();
        model.addAttribute("manegeForm", manegeForm);
        return "Form/manegeForm";
    }

    @RequestMapping(value = "/manege/add", method = RequestMethod.POST)
    public String saveManege(@ModelAttribute Manege manegeForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess && tableController.checkIdInTable("manege", manegeForm.getId()) && tableController.checkAddressInTable("manege", manegeForm.getAddress())) {
            manegeDAO.saveManege(manegeForm);
            return "redirect:/manege/list";
        } else {
            return "redirect:/manege/add";
        }
    }

    @RequestMapping(value = "/manege/edit/{id}", method = RequestMethod.GET)
    public String editManege(Model model, @PathVariable Integer id) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        Manege manege = manegeDAO.getManegeById(id);
        globalBufferId = id;
        model.addAttribute("manege", manege);
        return "Edit/editManege";
    }

    @RequestMapping(value = "/manege/edit", method = RequestMethod.POST)
    public String saveEditManege(@ModelAttribute Manege manege, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess && tableController.checkUpdateAddress("manege", manege.getAddress(), globalBufferId)) {
            manege.setId(globalBufferId);
            manegeDAO.updateManege(manege);
        }
        return "redirect:/manege/list";
    }


    @RequestMapping(value = "/manege/delete/{id}", method = RequestMethod.GET)
    public String deleteManege(Model model, @PathVariable Integer id) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess)
            manegeDAO.deleteManege(id);
        return "redirect:/manege/list";
    }

    //-----------------------------------------------------------------------------------


    //----------------Sport gym---------------------------------------------------------


    @RequestMapping(value = "/sportGym/list", method = RequestMethod.GET)
    public String getSportGym(Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        model = setAccess(model);
        if (flagFilter == false) {
            QueryForm queryForm = new QueryForm();
            model.addAttribute("sportGyms", bufferSportGymList);
            model.addAttribute("queryForm", queryForm);
            flagFilter = true;
        } else {
            List<SportGym> sportGyms = sportGymDAO.getAllSportGym();
            QueryForm queryForm = new QueryForm();
            model.addAttribute("sportGyms", sportGyms);
            model.addAttribute("queryForm", queryForm);
        }
        return "List/sportGymList";
    }

    @RequestMapping(value = "/sportGym/list", method = RequestMethod.POST)
    public String filterSportGym(@ModelAttribute QueryForm queryForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        List<SportGym> result = new ArrayList<>(100);
        flagFilter = false;
        if (queryForm.getNum() == 0) {
            return "redirect:/sportGym/list";
        } else if (queryForm.getNum() != 0) {
            sportGymDAO.getAllSportGym().stream().filter(sportGym -> sportGym.getNumberOfSimulators() < queryForm.getNum()).forEach(result::add);
            bufferSportGymList = result;
            return "redirect:/sportGym/list";
        }
        return "redirect:/sportGym/list";
    }

    @RequestMapping(value = "/sportGym/add", method = RequestMethod.GET)
    public String addSportGym(Model model) {
        if (flagAccess == null)
            return "redirect:/title";
        SportGym sportGymForm = new SportGym();
        model.addAttribute("sportGymForm", sportGymForm);
        return "Form/sportGymForm";
    }

    @RequestMapping(value = "/sportGym/add", method = RequestMethod.POST)
    public String saveSportGym(@ModelAttribute SportGym sportGymForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess && tableController.checkIdInTable("sport_gym", sportGymForm.getId()) && tableController.checkAddressInTable("sport_gym", sportGymForm.getAddress()) && sportGymForm.getNumberOfSimulators() > 0) {
            sportGymDAO.saveSportGym(sportGymForm);
            return "redirect:/sportGym/list";
        }
        return "redirect:/sportGym/add";
    }

    @RequestMapping(value = "/sportGym/edit/{id}", method = RequestMethod.GET)
    public String editSportGym(Model model, @PathVariable Integer id) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        SportGym sportGym = sportGymDAO.getSportGymById(id);
        globalBufferId = id;
        model.addAttribute("sportGym", sportGym);
        return "Edit/editSportGym";
    }

    @RequestMapping(value = "/sportGym/edit", method = RequestMethod.POST)
    public String saveEditSportGym(@ModelAttribute SportGym sportGym, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess && tableController.checkUpdateAddress("sport_gym", sportGym.getAddress(), globalBufferId) && sportGym.getNumberOfSimulators() > 0) {
            sportGym.setId(globalBufferId);
            sportGymDAO.updateSportGym(sportGym);
        }
        return "redirect:/sportGym/list";
    }


    @RequestMapping(value = "/sportGym/delete/{id}", method = RequestMethod.GET)
    public String deleteSportGym(Model model, @PathVariable Integer id) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess)
            sportGymDAO.deleteSportGym(id);
        return "redirect:/sportGym/list";
    }

    //-----------------------------------------------------------------------------------


    //---------------Court-------------------------------------------------------------

    @RequestMapping(value = "/court/list", method = RequestMethod.GET)
    public String getCourt(Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        model = setAccess(model);
        if (flagFilter == false) {
            QueryForm queryForm = new QueryForm();
            model.addAttribute("courts", bufferCourtList);
            model.addAttribute("queryForm", queryForm);
            flagFilter = true;
        } else {
            List<Court> courts = courtDAO.getAllCourt();
            QueryForm queryForm = new QueryForm();
            model.addAttribute("courts", courts);
            model.addAttribute("queryForm", queryForm);
        }
        return "List/courtList";
    }

    @RequestMapping(value = "/court/list", method = RequestMethod.POST)
    public String filterCourt(@ModelAttribute QueryForm queryForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        List<Court> result = new ArrayList<>(100);
        flagFilter = false;
        if (queryForm.getNum() == 0) {
            return "redirect:/court/list";
        } else if (queryForm.getNum() != 0) {
            courtDAO.getAllCourt().stream().filter(court -> court.getNumberOfPlayground() < queryForm.getNum()).forEach(result::add);
            bufferCourtList = result;
            return "redirect:/court/list";
        }
        return "redirect:/court/list";
    }

    @RequestMapping(value = "/court/add", method = RequestMethod.GET)
    public String addCourt(Model model) {
        if (flagAccess == null)
            return "redirect:/title";
        Court courtForm = new Court();
        model.addAttribute("courtForm", courtForm);
        return "Form/courtForm";
    }

    @RequestMapping(value = "/court/add", method = RequestMethod.POST)
    public String saveCourt(@ModelAttribute Court courtForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess && tableController.checkIdInTable("court", courtForm.getId()) && tableController.checkAddressInTable("court", courtForm.getAddress()) && courtForm.getNumberOfPlayground() > 0) {
            courtDAO.saveCourt(courtForm);
            return "redirect:/court/list";
        }
        return "redirect:/court/add";
    }

    @RequestMapping(value = "/court/edit/{id}", method = RequestMethod.GET)
    public String editCourt(Model model, @PathVariable Integer id) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        Court court = courtDAO.getCourtById(id);
        globalBufferId = id;
        model.addAttribute("court", court);
        return "Edit/editCourt";
    }

    @RequestMapping(value = "/court/edit", method = RequestMethod.POST)
    public String saveEditCourt(@ModelAttribute Court court, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess && tableController.checkUpdateAddress("court", court.getAddress(), globalBufferId) && court.getNumberOfPlayground() > 0) {
            court.setId(globalBufferId);
            courtDAO.updateCourt(court);
        }
        return "redirect:/court/list";
    }


    @RequestMapping(value = "/court/delete/{id}", method = RequestMethod.GET)
    public String deleteCourt(Model model, @PathVariable Integer id) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess)
            courtDAO.deleteCourt(id);
        return "redirect:/court/list";
    }

    //---------------------------------------------------------------------------------


    //------------------------Swimming Pool---------------------------------------------------------

    @RequestMapping(value = "/swimmingPool/list", method = RequestMethod.GET)
    public String getSwimmingPool(Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        model = setAccess(model);
        if (flagFilter == false) {
            QueryForm queryForm = new QueryForm();
            model.addAttribute("swimmingPools", bufferSwimmingPoolList);
            model.addAttribute("queryForm", queryForm);
            flagFilter = true;
        } else {
            List<SwimmingPool> swimmingPools = swimmingPoolDAO.getAllSwimmingPool();
            QueryForm queryForm = new QueryForm();
            model.addAttribute("swimmingPools", swimmingPools);
            model.addAttribute("queryForm", queryForm);
        }
        return "List/swimmingPoolList";
    }

    @RequestMapping(value = "/swimmingPool/list", method = RequestMethod.POST)
    public String filterSwimmingPool(@ModelAttribute QueryForm queryForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        List<SwimmingPool> result = new ArrayList<>(100);
        flagFilter = false;
        if (queryForm.getNum() == 0 && queryForm.getNum2() == 0) {
            return "redirect:/swimmingPool/list";
        } else if (queryForm.getNum() != 0 && queryForm.getNum2() == 0) {
            swimmingPoolDAO.getAllSwimmingPool().stream().filter(swimmingPool -> swimmingPool.getDepth() < queryForm.getNum()).forEach(result::add);
            bufferSwimmingPoolList = result;
            return "redirect:/swimmingPool/list";
        } else if (queryForm.getNum() == 0 && queryForm.getNum2() != 0) {
            swimmingPoolDAO.getAllSwimmingPool().stream().filter(swimmingPool -> swimmingPool.getNumberOfTower() < queryForm.getNum2()).forEach(result::add);
            bufferSwimmingPoolList = result;
            return "redirect:/swimmingPool/list";
        } else if (queryForm.getNum() != 0 && queryForm.getNum2() != 0) {
            swimmingPoolDAO.getAllSwimmingPool().stream().filter(swimmingPool -> swimmingPool.getDepth() < queryForm.getNum() && swimmingPool.getNumberOfTower() < queryForm.getNum2()).forEach(result::add);
            bufferSwimmingPoolList = result;
            return "redirect:/swimmingPool/list";
        }
        return "redirect:/swimmingPool/list";
    }

    @RequestMapping(value = "/swimmingPool/add", method = RequestMethod.GET)
    public String addSwimmingPool(Model model) {
        if (flagAccess == null)
            return "redirect:/title";
        SwimmingPool swimmingPoolForm = new SwimmingPool();
        model.addAttribute("swimmingPoolForm", swimmingPoolForm);
        return "Form/swimmingPoolForm";
    }

    @RequestMapping(value = "/swimmingPool/add", method = RequestMethod.POST)
    public String saveSwimmingPool(@ModelAttribute SwimmingPool swimmingPoolForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess && tableController.checkIdInTable("swimming_pool", swimmingPoolForm.getId()) && tableController.checkAddressInTable("swimming_pool", swimmingPoolForm.getAddress()) && swimmingPoolForm.getDepth() > 0 && swimmingPoolForm.getNumberOfTower() >= 0) {
            swimmingPoolDAO.saveSwimmingPool(swimmingPoolForm);
            return "redirect:/swimmingPool/list";
        }
        return "redirect:/swimmingPool/add";
    }

    @RequestMapping(value = "/swimmingPool/edit/{id}", method = RequestMethod.GET)
    public String editSwimmingPool(Model model, @PathVariable Integer id) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        SwimmingPool swimmingPool = swimmingPoolDAO.getSwimmingPoolById(id);
        globalBufferId = id;
        model.addAttribute("swimmingPool", swimmingPool);
        return "Edit/editSwimmingPool";
    }

    @RequestMapping(value = "/swimmingPool/edit", method = RequestMethod.POST)
    public String saveEditSwimmingPool(@ModelAttribute SwimmingPool swimmingPool, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess && tableController.checkUpdateAddress("swimming_pool", swimmingPool.getAddress(), globalBufferId) && swimmingPool.getDepth() > 0 && swimmingPool.getNumberOfTower() >= 0) {
            swimmingPool.setId(globalBufferId);
            swimmingPoolDAO.updateSwimmingPool(swimmingPool);
        }
        return "redirect:/swimmingPool/list";
    }


    @RequestMapping(value = "/swimmingPool/delete/{id}", method = RequestMethod.GET)
    public String deleteSwimmingPool(Model model, @PathVariable Integer id) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess)
            swimmingPoolDAO.deleteSwimmingPool(id);
        return "redirect:/swimmingPool/list";
    }

    //--------------------------------------------------------------------------------


    //-----------------------Track----------------------------------------------------

    @RequestMapping(value = "/track/list", method = RequestMethod.GET)
    public String getTrack(Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        model = setAccess(model);
        if (flagFilter == false) {
            QueryForm queryForm = new QueryForm();
            model.addAttribute("tracks", bufferTrackList);
            model.addAttribute("queryForm", queryForm);
            flagFilter = true;
        } else {
            List<Track> tracks = trackDAO.getAllTrack();
            QueryForm queryForm = new QueryForm();
            model.addAttribute("tracks", tracks);
            model.addAttribute("queryForm", queryForm);
        }
        return "List/trackList";
    }

    @RequestMapping(value = "/track/list", method = RequestMethod.POST)
    public String filterTrack(@ModelAttribute QueryForm queryForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        List<Track> result = new ArrayList<>(100);
        flagFilter = false;
        if (queryForm.getString().equals("") && queryForm.getNum() == 0) {
            return "redirect:/track/list";
        } else if (!(queryForm.getString().equals("")) && queryForm.getNum() == 0) {
            trackDAO.getAllTrack().stream().filter(track -> track.getTypeOfCover().equals(queryForm.getString())).forEach(result::add);
            bufferTrackList = result;
            return "redirect:/track/list";
        } else if (queryForm.getString().equals("") && queryForm.getNum() != 0) {
            trackDAO.getAllTrack().stream().filter(track -> track.getLengthTrack() < queryForm.getNum()).forEach(result::add);
            bufferTrackList = result;
            return "redirect:/track/list";
        } else if (!(queryForm.getString().equals("")) && queryForm.getNum() != 0) {
            trackDAO.getAllTrack().stream().filter(track -> track.getTypeOfCover().equals(queryForm.getString()) && track.getLengthTrack() < queryForm.getNum()).forEach(result::add);
            bufferTrackList = result;
            return "redirect:/track/list";
        }
        return "redirect:/track/list";
    }

    @RequestMapping(value = "/track/add", method = RequestMethod.GET)
    public String addTrack(Model model) {
        if (flagAccess == null)
            return "redirect:/title";
        Track trackForm = new Track();
        model.addAttribute("trackForm", trackForm);
        return "Form/trackForm";
    }

    @RequestMapping(value = "/track/add", method = RequestMethod.POST)
    public String saveTrack(@ModelAttribute Track trackForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess && tableController.checkIdInTable("track", trackForm.getId()) && tableController.checkAddressInTable("track", trackForm.getAddress()) && trackForm.getLengthTrack() > 0) {
            trackDAO.saveTrack(trackForm);
            return "redirect:/track/list";
        }
        return "redirect:/track/add";
    }

    @RequestMapping(value = "/track/edit/{id}", method = RequestMethod.GET)
    public String editTrack(Model model, @PathVariable Integer id) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        Track track = trackDAO.getTrackById(id);
        globalBufferId = id;
        model.addAttribute("track", track);
        return "Edit/editTrack";
    }

    @RequestMapping(value = "/track/edit", method = RequestMethod.POST)
    public String saveEditTrack(@ModelAttribute Track track, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess && tableController.checkUpdateAddress("track", track.getAddress(), globalBufferId) && track.getLengthTrack() > 0) {
            track.setId(globalBufferId);
            trackDAO.updateTrack(track);
        }
        return "redirect:/track/list";
    }


    @RequestMapping(value = "/track/delete/{id}", method = RequestMethod.GET)
    public String deleteTrack(Model model, @PathVariable Integer id) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess)
            trackDAO.deleteTrack(id);
        return "redirect:/track/list";
    }

    //--------------------------------------------------------------------------------


    //--------------------------Board games-------------------------------------------

    @RequestMapping(value = "/boardGames/list", method = RequestMethod.GET)
    public String getBoardGames(Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        model = setAccess(model);
        if (flagFilter == false) {
            QueryForm queryForm = new QueryForm();
            model.addAttribute("boardGames", bufferBoardGamesList);
            model.addAttribute("queryForm", queryForm);
            flagFilter = true;
        } else {
            List<BoardGames> boardGames = boardGamesDAO.getAllBoardGames();
            QueryForm queryForm = new QueryForm();
            model.addAttribute("boardGames", boardGames);
            model.addAttribute("queryForm", queryForm);
        }
        return "List/boardGamesList";
    }

    @RequestMapping(value = "/boardGames/list", method = RequestMethod.POST)
    public String filterBoardGames(@ModelAttribute QueryForm queryForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        List<BoardGames> result = new ArrayList<>(100);
        flagFilter = false;
        if (queryForm.getNum() == 0) {
            return "redirect:/boardGames/list";
        } else if (queryForm.getNum() != 0) {
            boardGamesDAO.getAllBoardGames().stream().filter(boardGames -> boardGames.getNumberOfTable() < queryForm.getNum()).forEach(result::add);
            bufferBoardGamesList = result;
            return "redirect:/boardGames/list";
        }
        return "redirect:/boardGames/list";
    }

    @RequestMapping(value = "/boardGames/add", method = RequestMethod.GET)
    public String addBoardGames(Model model) {
        if (flagAccess == null)
            return "redirect:/title";
        BoardGames boardGamesForm = new BoardGames();
        model.addAttribute("boardGamesForm", boardGamesForm);
        return "Form/boardGamesForm";
    }

    @RequestMapping(value = "/boardGames/add", method = RequestMethod.POST)
    public String saveBoardGames(@ModelAttribute BoardGames boardGamesForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess && tableController.checkIdInTable("board_games", boardGamesForm.getId()) && tableController.checkAddressInTable("board_games", boardGamesForm.getAddress()) && boardGamesForm.getNumberOfTable() >= 0) {
            boardGamesDAO.saveBoardGames(boardGamesForm);
            return "redirect:/boardGames/list";
        }
        return "redirect:/boardGames/add";
    }

    @RequestMapping(value = "/boardGames/edit/{id}", method = RequestMethod.GET)
    public String editBoardGames(Model model, @PathVariable Integer id) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        BoardGames boardGames = boardGamesDAO.getBoardGamesById(id);
        globalBufferId = id;
        model.addAttribute("boardGames", boardGames);
        return "Edit/editBoardGames";
    }

    @RequestMapping(value = "/boardGames/edit", method = RequestMethod.POST)
    public String saveEditBoardGames(@ModelAttribute BoardGames boardGames, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess && tableController.checkUpdateAddress("board_games", boardGames.getAddress(), globalBufferId) && boardGames.getNumberOfTable() >= 0) {
            boardGames.setId(globalBufferId);
            boardGamesDAO.updateBoardGames(boardGames);
        }
        return "redirect:/boardGames/list";
    }


    @RequestMapping(value = "/boardGames/delete/{id}", method = RequestMethod.GET)
    public String deleteBoardGames(Model model, @PathVariable Integer id) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess)
            boardGamesDAO.deleteBoardGames(id);
        return "redirect:/boardGames/list";
    }

    //--------------------------------------------------------------------------------


    //--------------------------Ice Rink----------------------------------------------

    @RequestMapping(value = "/iceRink/list", method = RequestMethod.GET)
    public String getIceRink(Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        model = setAccess(model);
        if (flagFilter == false) {
            QueryForm queryForm = new QueryForm();
            model.addAttribute("iceRink", bufferIceRinkList);
            model.addAttribute("queryForm", queryForm);
            flagFilter = true;
        } else {
            List<IceRink> iceRinks = iceRinkDAO.getAllIceRink();
            QueryForm queryForm = new QueryForm();
            model.addAttribute("iceRink", iceRinks);
            model.addAttribute("queryForm", queryForm);
        }
        return "List/iceRinkList";
    }

    @RequestMapping(value = "/iceRink/list", method = RequestMethod.POST)
    public String filterIceRink(@ModelAttribute QueryForm queryForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        List<IceRink> result = new ArrayList<>(100);
        flagFilter = false;
        if (queryForm.getNum() == 0) {
            return "redirect:/iceRink/list";
        } else if (queryForm.getNum() != 0) {
            iceRinkDAO.getAllIceRink().stream().filter(iceRink -> iceRink.getSquare() < queryForm.getNum()).forEach(result::add);
            bufferIceRinkList = result;
            return "redirect:/iceRink/list";
        }
        return "redirect:/iceRink/list";
    }

    @RequestMapping(value = "/iceRink/add", method = RequestMethod.GET)
    public String addIceRink(Model model) {
        if (flagAccess == null)
            return "redirect:/title";
        IceRink iceRinkForm = new IceRink();
        model.addAttribute("iceRinkForm", iceRinkForm);
        return "Form/iceRinkForm";
    }

    @RequestMapping(value = "/iceRink/add", method = RequestMethod.POST)
    public String saveIceRink(@ModelAttribute IceRink iceRinkForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess && tableController.checkIdInTable("ice_rink", iceRinkForm.getId()) && tableController.checkAddressInTable("ice_rink", iceRinkForm.getAddress()) && iceRinkForm.getSquare() > 0) {
            iceRinkDAO.saveIceRink(iceRinkForm);
            return "redirect:/iceRink/list";
        }
        return "redirect:/iceRink/add";
    }

    @RequestMapping(value = "/iceRink/edit/{id}", method = RequestMethod.GET)
    public String editIceRink(Model model, @PathVariable Integer id) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        IceRink iceRink = iceRinkDAO.getIceRinkById(id);
        globalBufferId = id;
        model.addAttribute("iceRink", iceRink);
        return "Edit/editIceRink";
    }

    @RequestMapping(value = "/iceRink/edit", method = RequestMethod.POST)
    public String saveEditIceRink(@ModelAttribute IceRink iceRink, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess && tableController.checkUpdateAddress("ice_rink", iceRink.getAddress(), globalBufferId) && iceRink.getSquare() > 0) {
            iceRink.setId(globalBufferId);
            iceRinkDAO.updateIceRink(iceRink);
        }
        return "redirect:/iceRink/list";
    }


    @RequestMapping(value = "/iceRink/delete/{id}", method = RequestMethod.GET)
    public String deleteIceRink(Model model, @PathVariable Integer id) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess)
            iceRinkDAO.deleteIceRink(id);
        return "redirect:/iceRink/list";
    }

    //--------------------------------------------------------------------------------


    //----------------Trainer---------------------------------------------------------

    @RequestMapping(value = "/trainer/list", method = RequestMethod.GET)
    public String getTrainer(Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        model = setAccess(model);
        if (flagFilter == false) {
            QueryForm queryForm = new QueryForm();
            model.addAttribute("trainerForm", bufferTrainerFormList);
            model.addAttribute("queryForm", queryForm);
            flagFilter = true;
        } else {
            List<Trainer> trainers = trainerDAO.getAllTrainer();
            List<TrainerForm> trainerFormList = trainerDAO.toTrainerFrom(trainers);
            QueryForm queryForm = new QueryForm();
            model.addAttribute("trainerForm", trainerFormList);
            model.addAttribute("queryForm", queryForm);
        }
        return "List/trainerList";
    }

    @RequestMapping(value = "/trainer/list", method = RequestMethod.POST)
    public String filterTrainer(@ModelAttribute QueryForm queryForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        List<TrainerForm> result = new ArrayList<>(100);
        List<Trainer> trainer = new ArrayList<>(100);
        List<Sportsmen> buffer = new ArrayList<>(100);
        flagFilter = false;

        if (queryForm.getNum() != 0) {
            sportsmenDAO.getAllSportsmen().stream().filter(sportsmen -> sportsmen.getPersonalKey() == queryForm.getNum()).forEach(buffer::add);
            for (int i = 0; i < buffer.size(); i++)
                trainer.add(trainerDAO.getTrainerById(buffer.get(i).getTrainerId()));
            result = trainerDAO.toTrainerFrom(trainer);
            bufferTrainerFormList = result;
            return "redirect:/trainer/list";
        }
        return "redirect:/trainer/list";
    }

    @RequestMapping(value = "/trainer2/list", method = RequestMethod.POST)
    public String filterTrainerBySport(@ModelAttribute QueryForm queryForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        List<TrainerForm> result = new ArrayList<>(100);
        List<Trainer> trainer = trainerDAO.getAllTrainer();
        List<TrainerForm> trainerFormList = trainerDAO.toTrainerFrom(trainer);
        flagFilter = false;
        if (queryForm.getString().equals(""))
            return "redirect:/trainer/list";
        trainerFormList.stream().filter(trainerForm -> trainerForm.getSport().equals(queryForm.getString())).forEach(result::add);
        bufferTrainerFormList = result;
        model.addAttribute("trainerForm", bufferTrainerFormList);
        return "redirect:/trainer/list";
    }

    @RequestMapping(value = "/trainer/add", method = RequestMethod.GET)
    public String addTrainer(Model model) {
        if (flagAccess == null)
            return "redirect:/title";
        Trainer trainer = new Trainer();
        model.addAttribute("trainerForm", trainer);
        return "Form/trainerForm";
    }

    @RequestMapping(value = "/trainer/add", method = RequestMethod.POST)
    public String saveTrainer(@ModelAttribute Trainer trainer, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess && tableController.checkIdInTable("trainer", trainer.getId()) && tableController.checkSportById(trainer.getSportId()) && tableController.checkPersonalKey("trainer", trainer.getName(), trainer.getPersonalKey())) {
            trainerDAO.saveTrainer(trainer);
            return "redirect:/trainer/list";
        }
        return "redirect:/trainer/add";
    }

    @RequestMapping(value = "/trainer/edit/{id}", method = RequestMethod.GET)
    public String editTrainer(Model model, @PathVariable Integer id) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        Trainer trainer = trainerDAO.getTrainerById(id);
        globalBufferId = id;
        model.addAttribute("trainer", trainer);
        return "Edit/editTrainer";
    }

    @RequestMapping(value = "/trainer/edit", method = RequestMethod.POST)
    public String saveEditTrainer(@ModelAttribute Trainer trainer, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess) {
            trainer.setId(globalBufferId);
            trainerDAO.updateTrainer(trainer);
        }
        return "redirect:/trainer/list";
    }


    @RequestMapping(value = "/trainer/delete/{id}", method = RequestMethod.GET)
    public String deleteTrainer(Model model, @PathVariable Integer id) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess)
            trainerDAO.deleteTrainer(id);
        return "redirect:/trainer/list";
    }

    //--------------------------------------------------------------------------------


    //---------------------Sport------------------------------------------------------

    @RequestMapping(value = "/sport/list", method = RequestMethod.GET)
    public String getSport(Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        model = setAccess(model);
        List<Sport> sports = sportDAO.getAllSport();
        model.addAttribute("sports", sports);
        return "List/sportList";
    }

    @RequestMapping(value = "/sport/add", method = RequestMethod.GET)
    public String addSport(Model model) {
        if (flagAccess == null)
            return "redirect:/title";
        Sport sport = new Sport();
        model.addAttribute("sportForm", sport);
        return "Form/sportForm";
    }

    @RequestMapping(value = "/sport/add", method = RequestMethod.POST)
    public String saveSport(@ModelAttribute Sport sport, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess && tableController.checkIdInTable("sport", sport.getId()) && tableController.checkNameInTable("sport", sport.getName())) {
            sportDAO.saveSport(sport);
            return "redirect:/sport/list";
        }
        return "redirect:/sport/add";
    }

    @RequestMapping(value = "/sport/edit/{id}", method = RequestMethod.GET)
    public String editSport(Model model, @PathVariable Integer id) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        Sport sport = sportDAO.getSportById(id);
        globalBufferId = id;
        model.addAttribute("sport", sport);
        return "Edit/editSport";
    }

    @RequestMapping(value = "/sport/edit", method = RequestMethod.POST)
    public String saveEditSport(@ModelAttribute Sport sport, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess && tableController.checkNameInTable("sport", sport.getName())) {
            sport.setId(globalBufferId);
            sportDAO.updateSport(sport);
        }
        return "redirect:/sport/list";
    }


    @RequestMapping(value = "/sport/delete/{id}", method = RequestMethod.GET)
    public String deleteSport(Model model, @PathVariable Integer id) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess)
            sportDAO.deleteSport(id);
        return "redirect:/sport/list";
    }

    //--------------------------------------------------------------------------------


    //--------------------Sportsmen---------------------------------------------------

    @RequestMapping(value = "/sportsmen/list", method = RequestMethod.GET)
    public String getSportsmen(Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        model = setAccess(model);
        if (flagFilter == false) {
            QueryForm queryForm = new QueryForm();
            model.addAttribute("sportsmenFormList", bufferSportsmenFormList);
            model.addAttribute("queryForm", queryForm);
            flagFilter = true;
        } else {
            List<Sportsmen> sportsmen = sportsmenDAO.getAllSportsmen();
            List<SportsmenForm> sportsmenFormList = sportsmenDAO.toSportsmenForm(sportsmen);
            QueryForm queryForm = new QueryForm();
            model.addAttribute("sportsmenFormList", sportsmenFormList);
            model.addAttribute("queryForm", queryForm);
        }
        return "List/sportsmenList";
    }

    @RequestMapping(value = "/sportsmen/list", method = RequestMethod.POST)
    public String filterSportsmen(@ModelAttribute QueryForm queryForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        List<SportsmenForm> result = new ArrayList<>(100);
        List<Sportsmen> sportsmen = sportsmenDAO.getAllSportsmen();
        List<SportsmenForm> sportsmenFormList = sportsmenDAO.toSportsmenForm(sportsmen);
        flagFilter = false;
        if (queryForm.getString().equals("") && queryForm.getNum() == 0 && queryForm.getString2().equals("")) {
            return "redirect:/sportsmen/list";
        } else if (!(queryForm.getString().equals("")) && queryForm.getNum() == 0 && queryForm.getString2().equals("")) {
            sportsmenFormList.stream().filter(sportsmenForm -> sportsmenForm.getSport().equals(queryForm.getString())).forEach(result::add);
            bufferSportsmenFormList = result;
            return "redirect:/sportsmen/list";
        } else if (queryForm.getString().equals("") && queryForm.getNum() != 0 && queryForm.getString2().equals("")) {
            sportsmenFormList.stream().filter(sportsmenForm -> sportsmenForm.getIntSportCategory() > queryForm.getNum()).forEach(result::add);
            bufferSportsmenFormList = result;
            return "redirect:/sportsmen/list";
        } else if (!(queryForm.getString().equals("")) && queryForm.getNum() != 0 && queryForm.getString2().equals("")) {
            sportsmenFormList.stream().filter(sportsmenForm -> sportsmenForm.getIntSportCategory() > queryForm.getNum() && sportsmenForm.getSport().equals(queryForm.getString())).forEach(result::add);
            bufferSportsmenFormList = result;
            return "redirect:/sportsmen/list";
        } else if (queryForm.getString().equals("") && queryForm.getNum() == 0 && !(queryForm.getString2().equals(""))) {

            sportsmenFormList.stream().filter(sportsmenForm -> sportsmenForm.getTrainerName().equals(queryForm.getString2())).forEach(result::add);
            bufferSportsmenFormList = result;
            return "redirect:/sportsmen/list";
        } else if (!(queryForm.getString().equals("")) && queryForm.getNum() == 0 && !(queryForm.getString2().equals(""))) {
            sportsmenFormList.stream().filter(sportsmenForm -> sportsmenForm.getSport().equals(queryForm.getString()) && sportsmenForm.getTrainerName().equals(queryForm.getString2())).forEach(result::add);
            bufferSportsmenFormList = result;
            return "redirect:/sportsmen/list";
        } else if (queryForm.getString().equals("") && queryForm.getNum() != 0 && !(queryForm.getString2().equals(""))) {
            sportsmenFormList.stream().filter(sportsmenForm -> sportsmenForm.getIntSportCategory() > queryForm.getNum() && sportsmenForm.getTrainerName().equals(queryForm.getString2())).forEach(result::add);
            bufferSportsmenFormList = result;
            return "redirect:/sportsmen/list";
        } else if (!(queryForm.getString().equals("")) && queryForm.getNum() != 0 && !(queryForm.getString2().equals(""))) {
            sportsmenFormList.stream().filter(sportsmenForm -> sportsmenForm.getIntSportCategory() > queryForm.getNum() && sportsmenForm.getSport().equals(queryForm.getString()) && sportsmenForm.getTrainerName().equals(queryForm.getString2())).forEach(result::add);
            bufferSportsmenFormList = result;
            return "redirect:/sportsmen/list";
        }
        return "redirect:/sportsmen/list";
    }

    @RequestMapping(value = "/sportsmenIdQuery1/list", method = RequestMethod.GET)
    public String getSportsmenFromManySport(Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        model = setAccess(model);
        List<Sportsmen> sportsmen = sportsmenDAO.getAllSportsmen();
        List<SportsmenForm> sportsmenFormList = sportsmenDAO.toSportsmenForm(sportsmen);
        List<SportsmenForm> result = sportsmenDAO.getSportsmenFormFromManySport(sportsmenFormList);
        model.addAttribute("sportsmenFormList", result);
        return "List/sportsmenList";
    }

    @RequestMapping(value = "/sportsmenIdQuery2/list", method = RequestMethod.GET)
    public String getSportsmenFromNumberOfParticipationEqualZero(Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        model = setAccess(model);
        List<SportsmenForm> result = new ArrayList<>();
        List<Sportsmen> sportsmen = sportsmenDAO.getAllSportsmen();
        List<SportsmenForm> sportsmenFormList = sportsmenDAO.toSportsmenForm(sportsmen);
        sportsmenFormList.stream().filter(sportsmenForm -> sportsmenForm.getNumberOfParticipation() == 0).forEach(result::add);
        model.addAttribute("sportsmenFormList", result);
        return "List/sportsmenList";
    }

    @RequestMapping(value = "/sportsmen/add", method = RequestMethod.GET)
    public String addSportsmen(Model model) {
        if (flagAccess == null)
            return "redirect:/title";
        SportsmenForm sportsmenForm = new SportsmenForm();
        model.addAttribute("sportsmenForm", sportsmenForm);
        return "Form/sportsmenForm";
    }

    @RequestMapping(value = "/sportsmen/add", method = RequestMethod.POST)
    public String saveSportsmen(@ModelAttribute SportsmenForm sportsmenForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess && tableController.checkSaveDataInTableSportsmen(sportsmenForm)) {
            sportsmenDAO.saveSportsmen(sportsmenForm);
            return "redirect:/sportsmen/list";
        }
        return "redirect:/sportsmen/add";
    }

    @RequestMapping(value = "/sportsmen/edit/{id}", method = RequestMethod.GET)
    public String editSportsmen(Model model, @PathVariable Integer id) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        Sportsmen sportsmen = sportsmenDAO.getSportsmenById(id);
        List<Sportsmen> sportsmenList = new ArrayList<>(1);
        sportsmenList.add(sportsmen);
        List<SportsmenForm> sportsmenFormList = sportsmenDAO.toSportsmenForm(sportsmenList);
        globalBufferId = id;
        model.addAttribute("sportsmenFormList", sportsmenFormList.get(0));
        return "Edit/editSportsmen";
    }

    @RequestMapping(value = "/sportsmen/edit", method = RequestMethod.POST)
    public String saveEditSportsmen(@ModelAttribute SportsmenForm sportsmenForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess && tableController.checkSportCategory(sportsmenForm)) {
            sportsmenForm.setId(globalBufferId);
            sportsmenDAO.updateSportsmen(sportsmenForm);
        }
        return "redirect:/sportsmen/list";
    }


    @RequestMapping(value = "/sportsmen/delete/{id}", method = RequestMethod.GET)
    public String deleteSportsmen(Model model, @PathVariable Integer id) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess) {
            double start = System.currentTimeMillis();
            sportsmenDAO.deleteSportsmen(id);
            double finish = System.currentTimeMillis();
            System.out.println("Time(ms) : " + (finish - start));
        }
        return "redirect:/sportsmen/list";
    }

    //--------------------------------------------------------------------------------


    //----------------------Clubs------------------------------------------------------

    @RequestMapping(value = "/club/list", method = RequestMethod.GET)
    public String getClub(Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        model = setAccess(model);
        List<Club> clubs = clubDAO.getAllClub();
        List<ClubForm> clubFormList = clubDAO.toClubForm(clubs);
        QueryForm queryForm = new QueryForm();
        List<Sportsmen> sportsmenList = sportsmenDAO.getAllSportsmen();
        queryForm.setNum2((int) (sportsmenList.stream().filter(sportsmen -> sportsmen.getClubId() == globalBufferId).count()));
        model.addAttribute("clubFormList", clubFormList);
        model.addAttribute("queryForm", queryForm);

        return "List/clubList";
    }

    @RequestMapping(value = "/club/list", method = RequestMethod.POST)
    public String getCountSportsmenInClub(@ModelAttribute QueryForm queryForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        globalBufferId = queryForm.getNum();
        return "redirect:/club/list";
    }

    @RequestMapping(value = "/club/add", method = RequestMethod.GET)
    public String addClub(Model model) {
        if (flagAccess == null)
            return "redirect:/title";
        ClubForm clubForm = new ClubForm();
        model.addAttribute("clubForm", clubForm);
        return "Form/clubForm";
    }

    @RequestMapping(value = "/club/add", method = RequestMethod.POST)
    public String saveClub(@ModelAttribute ClubForm clubForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess && tableController.checkSaveDataInTableClub(clubForm)) {
            Club club = clubDAO.toClub(clubForm);
            clubDAO.saveClub(club);
            return "redirect:/club/list";
        }
        return "redirect:/club/add";
    }

    @RequestMapping(value = "/club/edit/{id}", method = RequestMethod.GET)
    public String editClub(Model model, @PathVariable Integer id) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        Club club = clubDAO.getClubById(id);
        List<Club> clubList = new ArrayList<>(1);
        clubList.add(club);
        List<ClubForm> clubFormList = clubDAO.toClubForm(clubList);
        globalBufferId = id;
        model.addAttribute("clubFormList", clubFormList.get(0));
        return "Edit/editClub";
    }

    @RequestMapping(value = "/club/edit", method = RequestMethod.POST)
    public String saveEditClub(@ModelAttribute ClubForm clubForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess && tableController.checkNameInTable("club", clubForm.getName())) {
            clubForm.setId(globalBufferId);
            clubDAO.updateClub(clubForm);
        }
        return "redirect:/club/list";
    }


    @RequestMapping(value = "/club/delete/{id}", method = RequestMethod.GET)
    public String deleteClub(Model model, @PathVariable Integer id) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess)
            clubDAO.deleteClub(id);
        return "redirect:/club/list";
    }

    //---------------------------------------------------------------------------------


    //-----------Organizes and history sports competitions--------------------------------

    @RequestMapping(value = "/organizesAndHistorySportsCompetitions/list", method = RequestMethod.GET)
    public String getHistory(Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        model = setAccess(model);
        if (flagFilter == false) {
            QueryForm queryForm = new QueryForm();
            model.addAttribute("historyFormList", bufferHistoryFormList);
            model.addAttribute("queryForm", queryForm);
            flagFilter = true;
        } else {
            List<History> historyList = historyDAO.getAllHistory();
            List<HistoryForm> historyFormList = historyDAO.toHistoryForm(historyList);
            QueryForm queryForm = new QueryForm();
            model.addAttribute("historyFormList", historyFormList);
            model.addAttribute("queryForm", queryForm);
        }
        return "List/organizesAndHistorySportsCompetitionsList";
    }

    @RequestMapping(value = "/organizesAndHistorySportsCompetitions/list", method = RequestMethod.POST)
    public String filterHistory(@ModelAttribute QueryForm queryForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        List<HistoryForm> result = new ArrayList<>(100);
        flagFilter = false;
        List<History> historyList = historyDAO.getAllHistory();
        List<HistoryForm> historyFormList = historyDAO.toHistoryForm(historyList);
        if (queryForm.getNum2() != 0) {
            historyFormList.stream().filter(historyForm -> historyForm.getId() == queryForm.getNum2()).forEach(result::add);
            bufferHistoryFormList = result;
            return "redirect:/organizesAndHistorySportsCompetitions/list";
        } else {
            if (queryForm.getString().equals("") && queryForm.getNum() == 0 && queryForm.getString2().equals("")) {

            } else if (!(queryForm.getString().equals("")) && queryForm.getNum() == 0 && queryForm.getString2().equals("")) {
                historyFormList.stream().filter(historyForm -> historyForm.compareDate(queryForm.getString()) == -1).forEach(result::add);
                bufferHistoryFormList = result;
                return "redirect:/organizesAndHistorySportsCompetitions/list";
            } else if (queryForm.getString().equals("") && queryForm.getNum() != 0 && queryForm.getString2().equals("")) {
                historyFormList.stream().filter(historyForm -> historyForm.getPersonalKey() == queryForm.getNum()).forEach(result::add);
                bufferHistoryFormList = result;
                return "redirect:/organizesAndHistorySportsCompetitions/list";
            } else if (!(queryForm.getString().equals("")) && queryForm.getNum() != 0 && queryForm.getString2().equals("")) {
                historyFormList.stream().filter(historyForm -> historyForm.getPersonalKey() == queryForm.getNum() && historyForm.compareDate(queryForm.getString()) == -1).forEach(result::add);
                bufferHistoryFormList = result;
                return "redirect:/organizesAndHistorySportsCompetitions/list";
            } else if (queryForm.getString().equals("") && queryForm.getNum() == 0 && !(queryForm.getString2().equals(""))) {

                historyFormList.stream().filter(historyForm -> historyForm.compareDate(queryForm.getString2()) == 1).forEach(result::add);
                bufferHistoryFormList = result;
                return "redirect:/organizesAndHistorySportsCompetitions/list";
            } else if (!(queryForm.getString().equals("")) && queryForm.getNum() == 0 && !(queryForm.getString2().equals(""))) {
                historyFormList.stream().filter(historyForm -> historyForm.compareDate(queryForm.getString()) == -1 && historyForm.compareDate(queryForm.getString2()) == 1).forEach(result::add);
                bufferHistoryFormList = result;
                return "redirect:/organizesAndHistorySportsCompetitions/list";
            } else if (queryForm.getString().equals("") && queryForm.getNum() != 0 && !(queryForm.getString2().equals(""))) {
                historyFormList.stream().filter(historyForm -> historyForm.getPersonalKey() == queryForm.getNum() && historyForm.compareDate(queryForm.getString2()) == 1).forEach(result::add);
                bufferHistoryFormList = result;
                return "redirect:/organizesAndHistorySportsCompetitions/list";
            } else if (!(queryForm.getString().equals("")) && queryForm.getNum() != 0 && !(queryForm.getString2().equals(""))) {
                historyFormList.stream().filter(historyForm -> historyForm.compareDate(queryForm.getString()) == -1 && historyForm.getPersonalKey() == queryForm.getNum() && historyForm.compareDate(queryForm.getString2()) == 1).forEach(result::add);
                bufferHistoryFormList = result;
                return "redirect:/organizesAndHistorySportsCompetitions/list";
            }
        }

        return "redirect:/organizesAndHistorySportsCompetitions/list";
    }

    @RequestMapping(value = "/historyFilter2/list", method = RequestMethod.POST)
    public String filterHistory2(@ModelAttribute QueryForm queryForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        List<HistoryForm> result = new ArrayList<>(100);
        List<History> historyList = historyDAO.getAllHistory();
        List<HistoryForm> historyFormList = historyDAO.toHistoryForm(historyList);
        flagFilter = false;
        if (queryForm.getString().equals("") && queryForm.getString2().equals("")) {
            return "redirect:/organizesAndHistorySportsCompetitions/list";
        } else if (queryForm.getString().equals("") && !(queryForm.getString2().equals(""))) {
            historyFormList.stream().filter(historyForm -> historyForm.getSport().equals(queryForm.getString2())).forEach(result::add);
        } else if (!(queryForm.getString().equals("")) && queryForm.getString2().equals("")) {
            historyFormList.stream().filter(historyForm -> historyForm.getAddress().equals(queryForm.getString())).forEach(result::add);
        } else if (!(queryForm.getString().equals("")) && !(queryForm.getString2().equals(""))) {
            historyFormList.stream().filter(historyForm -> historyForm.getSport().equals(queryForm.getString2()) && historyForm.getAddress().equals(queryForm.getString())).forEach(result::add);
        }
        bufferHistoryFormList = result;
        model.addAttribute("historyFormList", result);
        return "redirect:/organizesAndHistorySportsCompetitions/list";
    }

    @RequestMapping(value = "/historyFilter2/list", method = RequestMethod.GET)
    public String getFilterHistory2(Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        List<History> historyList = historyDAO.getAllHistory();
        List<HistoryForm> historyFormList = historyDAO.toHistoryForm(historyList);
        QueryForm queryForm = new QueryForm();
        model.addAttribute("queryForm", queryForm);
        model.addAttribute("historyFormList", historyFormList);
        return "List/organizesAndHistorySportsCompetitionsList";
    }

    @RequestMapping(value = "/organizesAndHistorySportsCompetitions/add", method = RequestMethod.GET)
    public String addHistory(Model model) {
        if (flagAccess == null)
            return "redirect:/title";
        HistoryForm historyForm = new HistoryForm();
        model.addAttribute("historyForm", historyForm);
        return "Form/organizesAndHistorySportsCompetitionsForm";
    }

    @RequestMapping(value = "/organizesAndHistorySportsCompetitions/add", method = RequestMethod.POST)
    public String saveHistory(@ModelAttribute HistoryForm historyForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess && tableController.checkSaveDataInTableHistory(historyForm)) {
            historyDAO.saveHistory(historyForm);
            return "redirect:/organizesAndHistorySportsCompetitions/list";
        }
        return "redirect:/organizesAndHistorySportsCompetitions/add";
    }

    @RequestMapping(value = "/organizesAndHistorySportsCompetitions/edit/{id}", method = RequestMethod.GET)
    public String editHistory(Model model, @PathVariable Integer id) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        History history = historyDAO.getHistoryById(id);
        List<History> historyList = new ArrayList<>(1);
        historyList.add(history);
        List<HistoryForm> historyFormList = historyDAO.toHistoryForm(historyList);
        globalBufferId = id;
        model.addAttribute("historyForm", historyFormList.get(0));
        return "Edit/editOrganizesAndHistorySportsCompetitions";
    }

    @RequestMapping(value = "/organizesAndHistorySportsCompetitions/edit", method = RequestMethod.POST)
    public String saveEditHistory(@ModelAttribute HistoryForm historyForm, Model model) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess && tableController.checkAddressInAllTable(historyForm.getAddress()) && tableController.checkDateToPattern(historyForm.getDate())) {
            historyForm.setId(globalBufferId);
            historyDAO.updateHistory(historyForm);
        }
        return "redirect:/organizesAndHistorySportsCompetitions/list";
    }


    @RequestMapping(value = "/organizesAndHistorySportsCompetitions/delete/{id}", method = RequestMethod.GET)
    public String deleteHistory(Model model, @PathVariable Integer id) throws SQLException {
        if (flagAccess == null)
            return "redirect:/title";
        if (flagAccess)
            historyDAO.deleteHistory(id);
        return "redirect:/organizesAndHistorySportsCompetitions/list";
    }


    //------------------------------------------------------------------------------------


    //-----------------Queries-------------------------------------------------------------

    @RequestMapping(value = "/queries/list", method = RequestMethod.GET)
    public String getQueries(Model model) {
        if (flagAccess == null)
            return "redirect:/title";
        setAccess(model);
        return "List/queriesList";
    }

    //-------------------------------------------------------------------------------------
}
