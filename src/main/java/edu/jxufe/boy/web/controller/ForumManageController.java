
package edu.jxufe.boy.web.controller;

import java.util.List;

import edu.jxufe.boy.dao.Page;
import edu.jxufe.boy.entity.Board;
import edu.jxufe.boy.entity.User;
import edu.jxufe.boy.service.ForumService;
import edu.jxufe.boy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * 
 *<br>
 * <b>类描述:</b>
 * 
 * <pre>
 *   论坛管理，这部分功能由论坛管理员操作，包括：创建论坛版块、指定论坛版块管理员、
 * 用户锁定/解锁。
 *</pre>
 * 
 * @see
 *@since
 */
@RequestMapping("/ForumManage")
@Controller
public class ForumManageController extends BaseController {
	@Autowired
	private ForumService forumService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "AllPage")
	public ModelAndView showAllPage(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("manager/managerPage");
		return modelAndView;
	}
	@RequestMapping(value = "manageDefaultPage")
	public ModelAndView showManageDefaultPage(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("manager/manageDefaultPage");
		return modelAndView;
	}
	@RequestMapping(value = "manageLeftPage")
	public ModelAndView showManageLeftPage(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("manager/manageLeftPage");
		return modelAndView;
	}
	@RequestMapping(value = "boardManage")
	public ModelAndView showboardManage(){
		List<Board> boards =  forumService.getAllBoards();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("boards",boards);
		modelAndView.setViewName("manager/boardManage");
		return modelAndView;
	}
	/**
	 * 列出所有的论坛模块
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView listAllBoards() {
		ModelAndView view =new ModelAndView();
		List<Board> boards = forumService.getAllBoards();
		view.addObject("boards", boards);
		view.setViewName("/listAllBoards");
		return view;
	}

	/**
	 *  添加一个主题帖
	 * @return
	 */
	@RequestMapping(value = "/forum/addBoardPage", method = RequestMethod.GET)
	public String addBoardPage() {
		return "/addBoard";
	}

	/**
	 * 添加一个主题帖
	 * @param board
	 * @return
	 */
	@RequestMapping(value = "/forum/addBoard", method = RequestMethod.POST)
	public String addBoard(Board board) {
		forumService.addBoard(board);
		return "/addBoardSuccess";
	}

	/**
	 * 指定论坛管理员的页面
	 * @return
	 */
	@RequestMapping(value = "/forum/setBoardManagerPage", method = RequestMethod.GET)
	public ModelAndView setBoardManagerPage() {
		ModelAndView view =new ModelAndView();
		List<Board> boards = forumService.getAllBoards();
		List<User> users = userService.getAllUsers();
		view.addObject("boards", boards);
		view.addObject("users", users);
		view.setViewName("/setBoardManager");
		return view;
	}
	
    /**
     * 设置版块管理
     * @param boardId
     * @param userName
     * @return
     */
	@RequestMapping(value = "/forum/setBoardManager", method = RequestMethod.POST)
	public ModelAndView setBoardManager(@RequestParam("userName") String userName
			,@RequestParam("boardId") String boardId) {
		ModelAndView view =new ModelAndView();
		User user = userService.getUserByUserName(userName);
		if (user == null) {
			view.addObject("errorMsg", "用户名(" + userName
					+ ")不存在");
			view.setViewName("/fail");
		} else {
			Board board = forumService.getBoardById(Integer.parseInt(boardId));
			user.getManBoards().add(board);
			userService.update(user);
			view.setViewName("/success");
		}
		return view;
	}

	/**
	 * 用户锁定及解锁管理页面
	 * @return
	 */
	@RequestMapping(value = "/forum/userLockManagePage", method = RequestMethod.GET)
	public ModelAndView userLockManagePage() {
		ModelAndView view =new ModelAndView();
		List<User> users = userService.getAllUsers();
		view.setViewName("/userLockManage");
		view.addObject("users", users);
		return view;
	}

	/**
	 * 用户锁定及解锁设定
	 * @param userName
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/forum/userLockManage", method = RequestMethod.POST)
	public ModelAndView userLockManage(@RequestParam("userName") String userName
			,@RequestParam("locked") String locked) {
		ModelAndView view =new ModelAndView();
        User user = userService.getUserByUserName(userName);
		if (user == null) {
			view.addObject("errorMsg", "用户名(" + userName
					+ ")不存在");
			view.setViewName("/fail");
		} else {
			user.setLocked(Integer.parseInt(locked));
			userService.update(user);
			view.setViewName("/success");
		}
		return view;
	}
}
