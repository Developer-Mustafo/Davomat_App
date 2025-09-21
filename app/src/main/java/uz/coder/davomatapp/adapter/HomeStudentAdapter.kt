package uz.coder.davomatapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import uz.coder.davomatapp.R
import uz.coder.davomatapp.model.Course
import uz.coder.davomatapp.model.Group
import uz.coder.davomatapp.model.StudentCourses

class HomeStudentAdapter(
    context: Context,
    private val courseGroups: List<StudentCourses>
) : BaseExpandableListAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getGroup(groupPosition: Int): Course =
        courseGroups[groupPosition].course

    override fun getGroupCount(): Int = courseGroups.size

    override fun getGroupId(groupPosition: Int): Long =
        courseGroups[groupPosition].course.id

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val view = convertView ?: inflater.inflate(R.layout.item_course, parent, false)
        val course = getGroup(groupPosition)

        val titleView = view.findViewById<TextView>(R.id.textViewCourseTitle)
        val descriptionView = view.findViewById<TextView>(R.id.textViewCourseDescription)

        titleView.text = course.title
        if (course.description.isNotEmpty()) {
            descriptionView.text = course.description
            descriptionView.visibility = View.VISIBLE
        } else {
            descriptionView.visibility = View.GONE
        }

        return view
    }

    override fun getChildrenCount(groupPosition: Int): Int =
        courseGroups[groupPosition].group.size

    override fun getChild(groupPosition: Int, childPosition: Int): Group =
        courseGroups[groupPosition].group[childPosition]

    override fun getChildId(groupPosition: Int, childPosition: Int): Long =
        getChild(groupPosition, childPosition).id

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val view = convertView ?: inflater.inflate(R.layout.item_group, parent, false)
        val group = getChild(groupPosition, childPosition)

        val titleView = view.findViewById<TextView>(R.id.textViewGroupTitle)
        titleView.text = group.title

        return view
    }

    override fun hasStableIds(): Boolean = true
    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true
}