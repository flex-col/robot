/*
 * {EasyGank}  Copyright (C) {2015}  {CaMnter}
 *
 * This program comes with ABSOLUTELY NO WARRANTY; for details type `show w'.
 * This is free software, and you are welcome to redistribute it
 * under certain conditions; type `show c' for details.
 *
 * The hypothetical commands `show w' and `show c' should show the appropriate
 * parts of the General Public License.  Of course, your program's commands
 * might be different; for a GUI interface, you would use an "about box".
 *
 * You should also get your employer (if you work as a programmer) or school,
 * if any, to sign a "copyright disclaimer" for the program, if necessary.
 * For more information on this, and how to apply and follow the GNU GPL, see
 * <http://www.gnu.org/licenses/>.
 *
 * The GNU General Public License does not permit incorporating your program
 * into proprietary programs.  If your program is a subroutine library, you
 * may consider it more useful to permit linking proprietary applications with
 * the library.  If this is what you want to do, use the GNU Lesser General
 * Public License instead of this License.  But first, please read
 * <http://www.gnu.org/philosophy/why-not-lgpl.html>.
 */

package com.app.turingrobot.widget

import android.content.Context
import android.graphics.*

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation

/**
 * Description：GlideCircleTransform
 * Created by：CaMnter
 * Time：2016-01-16 14:31
 */
class GlideCircleTransform(context: Context) : BitmapTransformation(context) {

    override fun transform(pool: BitmapPool, inBitmap: Bitmap?, destWidth: Int, destHeight: Int): Bitmap? {
        if (inBitmap == null) return null

        val size = Math.min(inBitmap.width, inBitmap.height)
        val x = (inBitmap.width - size) / 2
        val y = (inBitmap.height - size) / 2

        val squared = Bitmap.createBitmap(inBitmap, x, y, size, size)
        var result: Bitmap? = pool.get(size, size, Bitmap.Config.ARGB_8888)
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        }

        val canvas = Canvas(result!!)
        val paint = Paint()
        paint.shader = BitmapShader(squared, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP)
        paint.isAntiAlias = true
        val r = size / 2f
        canvas.drawCircle(r, r, r, paint)
        return result
    }


    override fun getId(): String {
        return javaClass.name
    }

}
