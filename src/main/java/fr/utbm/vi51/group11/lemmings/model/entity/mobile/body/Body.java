package fr.utbm.vi51.group11.lemmings.model.entity.mobile.body;

import java.util.List;
import java.util.Set;

import org.arakhne.afc.math.continous.object2d.Vector2f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.utbm.vi51.group11.lemmings.model.entity.mobile.DynamicEntity;
import fr.utbm.vi51.group11.lemmings.utils.enums.InfluenceType;
import fr.utbm.vi51.group11.lemmings.utils.interfaces.IControllable;
import fr.utbm.vi51.group11.lemmings.utils.interfaces.IPerceivable;
import fr.utbm.vi51.group11.lemmings.utils.misc.Action;
import fr.utbm.vi51.group11.lemmings.utils.misc.Frustrum;
import fr.utbm.vi51.group11.lemmings.utils.misc.Influence;

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

	/** Influences given by the agent to perform */
	protected Set<Influence>	m_influences;

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
	protected abstract boolean filterPerception(
			List<IPerceivable> _perception);

	public boolean addInfluence(
			final Influence _influence)
	{
		return m_influences.add(_influence);
	}

	public boolean removeInfluence(
			final Influence _influence)
	{
		return m_influences.remove(_influence);
	}

	/*----------------------------------------------*/

	/**
	 * Method used to add a speed influence to the body.
	 * 
	 * @param _speed
	 *            Speed influence of the body.
	 */
	@Override
	public void influenceSpeed(
			final Vector2f _speed)
	{
		if (!filterInfluence())
		{
			// m_influences.add(new Influence(InfluenceType.SPEED, _speed));
		}
	}

	/*----------------------------------------------*/

	/**
	 * Method used to add an acceleration influence to the body.
	 * 
	 * @param _acceleration
	 *            Acceleration influence of the body.
	 */
	@Override
	public void influenceAcceleration(
			final Vector2f _acceleration)
	{
		if (!filterInfluence())
		{
			m_influences.add(new Influence(InfluenceType.ACCELERATION, _acceleration));
		}
	}

	/*----------------------------------------------*/

	/**
	 * Method used to add an Action influence to the body.
	 * 
	 * @param _action
	 *            Action influence to perform for the body.
	 */
	@Override
	public void influenceAction(
			final Action _action)
	{
		if (!filterInfluence())
		{
			m_influences.add(new Influence(InfluenceType.ACTION, _action));
		}
	}

	/*----------------------------------------------*/

	/**
	 * Method used to filter the influence added to the body by the agent. If
	 * for example the body wants to move but is paralyzed, it will filter the
	 * speed influence.
	 * 
	 * @return True if the influence is filtered.</br>False otherwise.
	 */
	protected abstract boolean filterInfluence();

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