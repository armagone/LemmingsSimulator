package fr.utbm.vi51.group11.lemmings.model.entity.mobile.body;

import java.util.List;

import org.arakhne.afc.math.continous.object2d.Vector2f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.utbm.vi51.group11.lemmings.model.entity.mobile.DynamicEntity;
import fr.utbm.vi51.group11.lemmings.utils.frustrums.Frustrum;
import fr.utbm.vi51.group11.lemmings.utils.interfaces.IControllable;
import fr.utbm.vi51.group11.lemmings.utils.interfaces.IPerceivable;

/**
 * 
 * Class representing the body of an Entity, a physical object that obeys the
 * laws of the graphicsEngine. Associated with a "mind" (an Agent) it can ask
 * the environment to fill its percepton field (frustrum), filter its perception
 * with obstacles, ask the environment to filter its actions, and finally move
 * to a new destination. It also can be killed and revived.
 * 
 * @author jnovak
 *
 */
public abstract class Body extends DynamicEntity implements IControllable
{
	/** Logger of the class */
	@SuppressWarnings("unused")
	private final static Logger	s_LOGGER	= LoggerFactory.getLogger(Body.class);

	/** Tells of the body is alive or not. */
	protected boolean			m_alive;

	/** Field of perception of the body */
	protected Frustrum			m_frustrum;

	/*----------------------------------------------*/

	/**
	 * @return True if the body is still alive.</br>False otherwise.
	 */
	public boolean isAlive()
	{
		return m_alive;
	}

	/*----------------------------------------------*/

	/**
	 * @return Frustrum of the body.
	 */
	public Frustrum getFrustrum()
	{
		return m_frustrum;
	}

	/*----------------------------------------------*/

	/**
	 * Method used to ask the environment a list of Perceivable objects the body
	 * can see through its body's frustrum.
	 * 
	 * @return List of Perceivable objects computed by the environment according
	 *         to the body's frustrum.
	 */
	@Override
	public List<IPerceivable> getPerception()
	{
		List<IPerceivable> perception = m_environment.getPerceptions(this);
		filterPerception(perception);
		return perception;
	}

	/*----------------------------------------------*/

	/**
	 * Method used to filter the perception of the body. If Perceivable objects
	 * are hidden by some obstacle, the list of perceivable objects are to be
	 * updated.
	 * 
	 * @return True if the list has been totally filtered.</br>False otherwise.
	 */
	protected abstract boolean filterPerception(List<IPerceivable> _perception);

	/*----------------------------------------------*/

	/**
	 * Method used to move the object with a certain direction.
	 * 
	 * @param _direction
	 *            Direction where the body wants to mvoe to.
	 */
	@Override
	public void move(Vector2f _direction)
	{
		if (!filterAction())
		{
			m_environment.move(this, _direction);
		}
	}

	/*----------------------------------------------*/

	/**
	 * Method used to filter the action of the body. If for example the body
	 * wants to move but is paralyzed, it will filter the action of moving.
	 * 
	 * @return True if the action is filtered.</br>False otherwise.
	 */
	protected abstract boolean filterAction();

	/*----------------------------------------------*/

	/**
	 * Method used to kill the body.
	 */
	public void kill()
	{
		m_alive = false;
	}

	/*----------------------------------------------*/

	/**
	 * Method used to revive the body.
	 */
	public void revive()
	{
		m_alive = true;
	}
}